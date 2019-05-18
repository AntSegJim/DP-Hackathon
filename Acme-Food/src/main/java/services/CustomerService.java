
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

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.CreditCard;
import domain.Customer;
import domain.Finder;
import domain.Rating;
import forms.RegistrationFormCustomerAndCreditCard;

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository			customerRepository;

	@Autowired
	private CustomizableSystemService	customizableService;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;


	public Customer create() {
		final Customer res = new Customer();

		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setVatNumber("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurnames("");
		res.setAddress("");
		res.setCreditCard(new CreditCard());
		res.setRatings(new HashSet<Rating>());
		res.setFinder(new Finder());

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.CUSTOMER);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int customerId) {
		final Customer customer = this.customerRepository.findOne(customerId);
		final UserAccount userLoged = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(userLoged.getId());
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"));
		Assert.isTrue(customer.equals(a));
		return this.customerRepository.findOne(customerId);
	}

	public Customer save(final Customer r) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("COMPANY"), "Comprobar que hay Company conectado");
		Customer res = null;

		Assert.isTrue(r != null && r.getName() != null && r.getSurnames() != null && r.getName() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "Company.save -> Name, Surname or email invalid");
		Assert.isTrue(r.getVatNumber() != null, "Customer.save -> VatNumber  invalid");
		Assert.isTrue(r.getCreditCard() != null, "Customer.save -> CreditCard  invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "CustomerService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Customer.Email -> The email you entered is already being used");
		//		else {
		//			final Company a = this.companyRepository.findOne(r.getId());
		//			Assert.isTrue(a.getEmail().equals(r.getEmail()));
		//		}

		//NUEVO
		Assert.isTrue(r.getUserAccount().getUsername() != null && r.getUserAccount().getUsername() != "");
		Assert.isTrue(r.getUserAccount().getPassword() != null && r.getUserAccount().getPassword() != "");

		if (r.getId() == 0) {

			final Md5PasswordEncoder encoder;
			encoder = new Md5PasswordEncoder();
			final String hash = encoder.encodePassword(r.getUserAccount().getPassword(), null);
			final UserAccount user = r.getUserAccount();
			user.setPassword(hash);
		}

		res = this.customerRepository.save(r);
		return res;
	}

	public Customer getCustomerUserAccount(final Integer id) {
		return this.customerRepository.getCustomerByUserAccountId(id);
	}

	public Customer reconstruct(final RegistrationFormCustomerAndCreditCard registrationForm, final BindingResult binding) {
		Customer res = new Customer();

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
			res.setRatings(registrationForm.getRatings());
			res.setFinder(registrationForm.getFinder());

			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.CUSTOMER);
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

			Assert.isTrue(registrationForm.getCheck() == true);

		} else {
			res = this.customerRepository.findOne(registrationForm.getId());
			final Customer p = new Customer();

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
			p.setCreditCard(registrationForm.getCreditCard());
			p.setRatings(res.getRatings());
			p.setFinder(res.getFinder());

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

}
