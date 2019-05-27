
package services;

import java.util.ArrayList;
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

import repositories.RestaurantRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Rating;
import domain.Restaurant;
import forms.RegistrationFormRestaurant;

@Service
@Transactional
public class RestaurantService {

	@Autowired
	private RestaurantRepository		restaurantRepository;
	@Autowired
	private CustomizableSystemService	customizableService;
	@Autowired
	private ActorService				actorService;
	@Autowired
	private CustomerService				customerService;
	@Autowired
	private Validator					validator;


	public Restaurant create() {
		final Restaurant res = new Restaurant();

		res.setAddress("");
		res.setEmail("");
		res.setName("");
		res.setVatNumber("");
		final String telephoneCode = this.customizableService.getTelephoneCode();
		res.setPhone(telephoneCode + " ");
		res.setPhoto("");
		res.setSurnames("");
		res.setAddress("");

		res.setRatings(new HashSet<Rating>());
		res.setComercialName("");
		res.setSpeciality("");
		res.setIsBanned(0);
		res.setMediumScore(0);

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		ad.setAuthority(Authority.RESTAURANT);
		user.getAuthorities().add(ad);
		user.setUsername("");
		user.setPassword("");
		res.setUserAccount(user);

		return res;
	}

	public Collection<Restaurant> findAll() {
		return this.restaurantRepository.findAll();
	}

	public Restaurant findOne(final int restaurantId) {
		final Restaurant restaurant = this.restaurantRepository.findOne(restaurantId);
		final UserAccount userLoged = LoginService.getPrincipal();
		final Actor a = this.actorService.getActorByUserAccount(userLoged.getId());
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("RESTAURANT"));
		Assert.isTrue(restaurant.equals(a));
		return this.restaurantRepository.findOne(restaurantId);
	}

	public Restaurant findOneAdmin(final int restaurantId) {

		final UserAccount userLoged = LoginService.getPrincipal();
		Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		return this.restaurantRepository.findOne(restaurantId);
	}
	public Restaurant findOneSinAutenticar(final int restaurantId) {
		return this.restaurantRepository.findOne(restaurantId);
	}

	public Restaurant save(final Restaurant r) {

		//final UserAccount userLoged = LoginService.getPrincipal();
		//Assert.isTrue(userLoged.getAuthorities().iterator().next().getAuthority().equals("COMPANY"), "Comprobar que hay Company conectado");
		Restaurant res = null;
		Assert.isTrue(r.getComercialName() != null && r.getComercialName() != "", "Resturant.save -> comercialName  invalid");
		Assert.isTrue(r.getSpeciality() != null && r.getSpeciality() != "", "Resturant.save -> spaciality  invalid");
		Assert.isTrue(r != null && r.getName() != null && r.getSurnames() != null && r.getName() != "" && r.getUserAccount() != null && r.getEmail() != null && r.getEmail() != "", "Restaurant.save -> Name, Surname or email invalid");
		Assert.isTrue(r.getVatNumber() != null, "Companny.save -> VatNumber  invalid");

		final String regexEmail1 = "[^@]+@[^@]+\\.[a-zA-Z]{2,}";
		final Pattern patternEmail1 = Pattern.compile(regexEmail1);
		final Matcher matcherEmail1 = patternEmail1.matcher(r.getEmail());

		final String regexEmail2 = "^[A-z0-9]+\\s*[A-z0-9\\s]*\\s\\<[A-z0-9]+\\@[A-z0-9]+\\.[A-z0-9.]+\\>";
		final Pattern patternEmail2 = Pattern.compile(regexEmail2);
		final Matcher matcherEmail2 = patternEmail2.matcher(r.getEmail());
		Assert.isTrue(matcherEmail1.find() == true || matcherEmail2.find() == true, "ResturantService.save -> Correo inválido");

		final List<String> emails = this.actorService.getEmails();

		if (r.getId() == 0)
			Assert.isTrue(!emails.contains(r.getEmail()), "Resturant.Email -> The email you entered is already being used");
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

		res = this.restaurantRepository.save(r);
		return res;
	}

	public Restaurant getRestaurantByUserAccount(final Integer userAccountId) {
		return this.restaurantRepository.getRestaurantByUserAccount(userAccountId);

	}

	public Restaurant reconstruct(final RegistrationFormRestaurant registrationForm, final BindingResult binding) {
		Restaurant res = new Restaurant();

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

			res.setRatings(registrationForm.getRatings());
			res.setComercialName(registrationForm.getComercialName());
			res.setSpeciality(registrationForm.getSpeciality());
			res.setIsBanned(0);
			res.setMediumScore(0);
			res.setOrderTime(registrationForm.getOrderTime());

			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.RESTAURANT);
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
			res = this.restaurantRepository.findOne(registrationForm.getId());
			final Restaurant p = new Restaurant();

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

			p.setRatings(res.getRatings());
			p.setComercialName(registrationForm.getComercialName());
			p.setSpeciality(registrationForm.getSpeciality());
			p.setIsBanned(res.getIsBanned());
			p.setMediumScore(res.getMediumScore());
			p.setOrderTime(registrationForm.getOrderTime());

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

	public Collection<Restaurant> getActivesRestaurants() {
		return this.restaurantRepository.getActivesRestaurants();
	}

	public Integer getFreeDealersByRestauran(final Integer id) {
		return this.restaurantRepository.getFreeDealerByRestaurant(id);
	}

	public Collection<Restaurant> getRestaurantWithFood() {
		return this.restaurantRepository.getRestaurantWithFood();
	}

	public Collection<Restaurant> getAllRestaurantWhereIHaveDoneAOrder() {
		return this.restaurantRepository.getAllRestaurantWhereIHaveDoneAOrder(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()).getId());
	}

	public Collection<Restaurant> getAllMyRatings() {
		return this.restaurantRepository.getAllMyRatings(this.customerService.getCustomerUserAccount(LoginService.getPrincipal().getId()).getId());
	}

	//Dashboard
	public Collection<String> getRestaurantWithMoreScore() {
		return this.restaurantRepository.getRestaurantWithMoreScore();
	}

	public Collection<String> getRestaurantWithLessScore() {
		return this.restaurantRepository.getRestaurantWithLessScore();
	}

	public List<String> getTop5RestaurantsWithMoreOrders() {
		final List<Integer> ls = (List<Integer>) this.restaurantRepository.getTop5RestaurantsWithMoreOrders();
		final List<String> res = new ArrayList<String>();

		for (int i = 0; i < ls.size(); i++) {
			final Restaurant r = this.restaurantRepository.findOne(ls.get(i));
			res.add(r.getComercialName());
		}

		return res;

	}

	public Collection<Restaurant> getRestaurantBan() {
		return this.restaurantRepository.getRestaurantBan();
	}
	public Restaurant reconstruct(final Restaurant restaurant, final BindingResult binding) {
		Restaurant res;

		if (restaurant.getId() == 0) {
			res = restaurant;
			final Authority ad = new Authority();
			final UserAccount user = new UserAccount();
			user.setAuthorities(new HashSet<Authority>());
			ad.setAuthority(Authority.RESTAURANT);
			user.getAuthorities().add(ad);
			res.setUserAccount(user);
			user.setUsername(restaurant.getUserAccount().getUsername());
			user.setPassword(restaurant.getUserAccount().getPassword());

			this.validator.validate(res, binding);
			return res;
		} else {
			res = this.restaurantRepository.findOne(restaurant.getId());
			final Restaurant p = new Restaurant();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setAddress(res.getAddress());
			p.setEmail(res.getEmail());
			p.setVatNumber(res.getVatNumber());
			p.setName(res.getName());
			p.setPhone(res.getPhone());
			p.setPhoto(res.getPhoto());
			p.setSurnames(res.getSurnames());
			p.setUserAccount(res.getUserAccount());

			p.setRatings(res.getRatings());
			p.setComercialName(res.getComercialName());
			p.setSpeciality(res.getSpeciality());
			p.setIsBanned(restaurant.getIsBanned());
			p.setMediumScore(res.getMediumScore());
			p.setOrderTime(res.getOrderTime());

			if (p.getIsBanned() == 1) {
				final Collection<Authority> result = new ArrayList<Authority>();
				Authority authority;
				authority = new Authority();
				authority.setAuthority(Authority.BANNED);
				result.add(authority);
				p.getUserAccount().setAuthorities(result);
				//--------------------------------------------

			} else if (p.getIsBanned() == 0) {
				final Collection<Authority> result = new ArrayList<Authority>();
				Authority authority;
				authority = new Authority();
				authority.setAuthority(Authority.RESTAURANT);
				result.add(authority);
				p.getUserAccount().setAuthorities(result);
			}

			this.validator.validate(p, binding);
			return p;
		}

	}

}
