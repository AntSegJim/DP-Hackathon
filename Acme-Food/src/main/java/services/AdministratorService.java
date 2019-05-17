
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import forms.RegistrationForm;

@Service
@Transactional
public class AdministratorService {

	@Autowired
	private AdministratorRepository	adminRepo;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	public Administrator create() {
		final Administrator admin = new Administrator();
		admin.setName("");
		admin.setVatNumber("");
		admin.setSurnames("");
		admin.setPhoto("");
		admin.setEmail("");
		admin.setPhone("");
		admin.setAddress("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.ADMIN);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		admin.setUserAccount(user);

		return admin;
	}

	//Listing
	public Collection<Administrator> findAll() {
		return this.adminRepo.findAll();
	}
	public Administrator findOne(final int adminId) {
		final Administrator admin = this.adminRepo.findOne(adminId);
		final UserAccount userLoged = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(userLoged.getId());
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));
		Assert.isTrue(admin.equals(a));
		return this.adminRepo.findOne(adminId);
	}

	//Update
	public Administrator save(final Administrator admin) {
		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"), "Comprobar que hay admin conectado");

		Administrator res = null;
		Assert.isTrue(admin.getName() != null && admin.getName() != "" && admin.getVatNumber() != null && admin.getSurnames() != null && admin.getUserAccount() != null && admin.getEmail() != null && admin.getEmail() != "", "Fallo en datos personales");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(admin.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(admin.getEmail());

		final String regexEmail3 = "^[A-z0-9]+\\@$";
		final Pattern patternEmail3 = Pattern.compile(regexEmail3);
		final Matcher matcherEmail3 = patternEmail3.matcher(admin.getEmail());

		final String regexEmail4 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@\\>$";
		final Pattern patternEmail4 = Pattern.compile(regexEmail4);
		final Matcher matcherEmail4 = patternEmail4.matcher(admin.getEmail());

		Assert.isTrue((matcherEmail1.matches() == true || matcherEmail2.matches() == true || matcherEmail3.matches() == true || matcherEmail4.matches() == true), "Email");

		final List<String> emails = this.actorService.getEmails();

		if (admin.getId() == 0)
			Assert.isTrue(!emails.contains(admin.getEmail()));
		//		else {
		//			final Administrator a = this.adminRepo.findOne(admin.getId());
		//			Assert.isTrue(a.getEmail().equals(admin.getEmail()));
		//		}

		//NUEVO
		Assert.isTrue(admin.getUserAccount().getUsername() != null && admin.getUserAccount().getUsername() != "", "Cuenta");
		Assert.isTrue(admin.getUserAccount().getPassword() != null && admin.getUserAccount().getPassword() != "", "Cuenta");

		if (admin.getId() == 0) {
			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(admin.getUserAccount().getPassword(), null);
			final UserAccount user = admin.getUserAccount();
			user.setPassword(hash);
		}

		res = this.adminRepo.save(admin);

		return res;
	}

	public Administrator getAdministratorByUserAccount(final int userAccountId) {
		return this.adminRepo.getAdministratorByUserAccount(userAccountId);
	}

	public Administrator reconstruct(final RegistrationForm registrationForm, final BindingResult binding) {
		Administrator res = new Administrator();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getUserAccount().getId());
			res.setVersion(registrationForm.getVersion());
			res.setAddress(registrationForm.getAddress());
			res.setEmail(registrationForm.getEmail());
			res.setVatNumber(registrationForm.getVatNumber());
			res.setName(registrationForm.getName());
			res.setPhone(registrationForm.getPhone());
			res.setPhoto(registrationForm.getPhoto());
			res.setSurnames(registrationForm.getSurnames());
			res.setCreditCard(registrationForm.getCreditCard());
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.ADMIN);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(registrationForm.getUserAccount().getUsername());
			user.setPassword(registrationForm.getUserAccount().getPassword());

			Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));

			if (res.getPhone().length() <= 5)
				res.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(res.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}

			this.validator.validate(res, binding);

		} else {

			res = this.adminRepo.findOne(registrationForm.getId());
			final Administrator a = new Administrator();

			if (registrationForm.getUserAccount().getPassword().equals("") && registrationForm.getPassword().equals(""))
				a.setUserAccount(res.getUserAccount());
			else {
				final UserAccount user = registrationForm.getUserAccount();
				final Md5PasswordEncoder encoder;
				encoder = new Md5PasswordEncoder();
				final String hash = encoder.encodePassword(registrationForm.getUserAccount().getPassword(), null);
				user.setPassword(hash);
				registrationForm.setUserAccount(user);

				if (!registrationForm.getUserAccount().getPassword().equals(res.getUserAccount().getPassword())) {
					final Md5PasswordEncoder encoder2;
					encoder2 = new Md5PasswordEncoder();
					final String hash2 = encoder2.encodePassword(registrationForm.getPassword(), null);
					registrationForm.setPassword(hash2);

					Assert.isTrue(registrationForm.getPassword().equals(registrationForm.getUserAccount().getPassword()));

				}

				a.setUserAccount(res.getUserAccount());
				a.getUserAccount().setPassword(registrationForm.getUserAccount().getPassword());

			}

			a.setId(res.getId());
			a.setVersion(res.getVersion());
			a.setAddress(registrationForm.getAddress());
			a.setEmail(registrationForm.getEmail());
			a.setVatNumber(registrationForm.getVatNumber());
			a.setName(registrationForm.getName());
			a.setPhone(registrationForm.getPhone());
			a.setPhoto(registrationForm.getPhoto());
			a.setSurnames(registrationForm.getSurnames());
			a.setCreditCard(registrationForm.getCreditCard());

			if (a.getPhone().length() <= 5)
				a.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(a.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "BrotherhoodService.save -> Telefono no valido");
			}

			a.getUserAccount().setUsername(registrationForm.getUserAccount().getUsername());

			this.validator.validate(a, binding);
			res = a;
		}
		return res;
	}

	public Administrator reconstruct(final Administrator administrator, final BindingResult binding) {
		Administrator res;

		if (administrator.getId() == 0) {
			res = administrator;
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.ADMIN);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(administrator.getUserAccount().getUsername());
			user.setPassword(administrator.getUserAccount().getPassword());

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.adminRepo.findOne(administrator.getId());
			final Administrator p = new Administrator();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(administrator.getAddress());
			p.setEmail(administrator.getEmail());
			p.setVatNumber(administrator.getVatNumber());
			p.setName(administrator.getName());
			p.setPhone(administrator.getPhone());
			p.setPhoto(administrator.getPhoto());
			p.setSurnames(administrator.getSurnames());
			p.setUserAccount(res.getUserAccount());

			this.validator.validate(p, binding);
			return p;
		}

	}
}
