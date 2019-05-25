
package services;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.DealerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Dealer;
import forms.RegistrationFormDealer;

@Service
@Transactional
public class DealerService {

	@Autowired
	private DealerRepository			dealerRepository;
	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private RestaurantService			restaurantService;
	@Autowired
	private Validator					validator;


	public Dealer create() {
		final Dealer res = new Dealer();

		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setVatNumber("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurnames("");
		res.setAddress("");

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.DEALER);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Dealer> findAll() {
		return this.dealerRepository.findAll();
	}

	public Dealer findOne(final int dealerId) {
		return this.dealerRepository.findOne(dealerId);
	}

	public Dealer save(final Dealer d) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("COMPANY"), "Comprobar que hay Company conectado");
		Dealer res = null;

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(d.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(d.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "ResturantService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (d.getId() == 0)
			Assert.isTrue(!emails.contains(d.getEmail()), "Resturant.Email -> The email you entered is already being used");
		//		else {
		//			final Company a = this.companyRepository.findOne(r.getId());
		//			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		//		}

		//NUEVO
		Assert.isTrue(d.getUserAccount().getUsername() != null && d.getUserAccount().getUsername() != "");
		Assert.isTrue(d.getUserAccount().getPassword() != null && d.getUserAccount().getPassword() != "");

		if (d.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(d.getUserAccount().getPassword(), null);
			final UserAccount user = d.getUserAccount();
			user.setPassword(hash);
		}

		res = this.dealerRepository.save(d);
		return res;
	}

	public Dealer getDealerByUserAccount(final Integer userAccountId) {
		return this.dealerRepository.getDealerByUserAccount(userAccountId);

	}

	public Dealer reconstruct(final RegistrationFormDealer registrationForm, final BindingResult binding) {
		Dealer res = new Dealer();

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

			res.setRestaurant(this.restaurantService.getRestaurantByUserAccount(LoginService.getPrincipal().getId()));

			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.DEALER);
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
				Assert.isTrue(matcherTelefono.find() == true, "CompanyService.save -> Telefono no valido");
			}
			this.validator.validate(res, binding);

		} else {
			res = this.dealerRepository.findOne(registrationForm.getId());
			final Dealer p = new Dealer();

			if (registrationForm.getUserAccount().getPassword().equals("") && registrationForm.getPassword().equals(""))
				p.setUserAccount(res.getUserAccount());
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

				p.setUserAccount(res.getUserAccount());
				p.getUserAccount().setPassword(registrationForm.getUserAccount().getPassword());

			}

			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(registrationForm.getAddress());
			p.setEmail(registrationForm.getEmail());
			p.setVatNumber(registrationForm.getVatNumber());
			p.setName(registrationForm.getName());
			p.setPhone(registrationForm.getPhone());
			p.setPhoto(registrationForm.getPhoto());
			p.setSurnames(registrationForm.getSurnames());

			p.setRestaurant(res.getRestaurant());

			if (p.getPhone().length() <= 5)
				p.setPhone("");

			if (registrationForm.getPatternPhone() == false) {
				final String regexTelefono = "^\\+[0-9]{0,3}\\s\\([0-9]{0,3}\\)\\ [0-9]{4,}$|^\\+[1-9][0-9]{0,2}\\ [0-9]{4,}$|^[0-9]{4,}|^\\+[0-9]\\ $|^$|^\\+$";
				final Pattern patternTelefono = Pattern.compile(regexTelefono);
				final Matcher matcherTelefono = patternTelefono.matcher(p.getPhone());
				Assert.isTrue(matcherTelefono.find() == true, "CompanyService.save -> Telefono no valido");
			}

			p.getUserAccount().setUsername(registrationForm.getUserAccount().getUsername());

			this.validator.validate(p, binding);
			res = p;

		}
		return res;

	}

	public Collection<Dealer> getActiveDealersByRestaurant(final Integer id) {
		return this.dealerRepository.getActiveDealersByRestaurant(id);
	}

	public Integer getNumberCashOrderByDealer(final Integer id) {
		return this.dealerRepository.getNumberCashOrderByDealer(id);
	}

	public Collection<Dealer> getAllDealerByRestaurantUserAccount(final int restaurantUserAccountId) {
		return this.dealerRepository.getAllDealerByRestaurantUserAccount(restaurantUserAccountId);
	}
}
