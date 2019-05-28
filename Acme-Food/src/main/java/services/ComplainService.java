
package services;

import java.util.Collection;

import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ComplainRepository;
import security.LoginService;
import security.UserAccount;
import domain.CashOrder;
import domain.Complain;
import domain.Customer;

@Service
@Transactional
public class ComplainService {

	@Autowired
	private ComplainRepository	complainRepository;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private ActorService		actorS;
	@Autowired
	private Validator			validator;


	public Complain create() {
		final Complain res = new Complain();
		final UserAccount user = this.actorS.getActorLogged().getUserAccount();
		res.setDescription("");
		res.setCustomer(this.customerService.getCustomerUserAccount(user.getId()));
		res.setCashOrder(new CashOrder());
		return res;
	}

	public Collection<Complain> findAll() {
		return this.complainRepository.findAll();
	}

	public Complain findOne(final int complainId) {
		final Complain complain = this.complainRepository.findOne(complainId);
		final UserAccount userAccount = LoginService.getPrincipal();
		final Customer customer = this.customerService.getCustomerUserAccount(userAccount.getId());
		Assert.isTrue(complain.getCustomer() == customer);
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"));
		return complain;
	}

	public Complain save(final Complain complain) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"));
		Assert.isTrue(complain.getCustomer().equals(this.customerService.getCustomerUserAccount(userAccount.getId())));
		final Complain complainSave = this.complainRepository.save(complain);
		return complainSave;
	}

	public void delete(final Complain complain) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.isTrue(userAccount.getAuthorities().iterator().next().getAuthority().equals("CUSTOMER"));
		Assert.isTrue(complain.getCustomer().equals(this.customerService.getCustomerUserAccount(userAccount.getId())));
		this.complainRepository.delete(complain);
	}

	public Complain reconstruct(final Complain complain, final BindingResult binding) {
		Complain res;
		if (complain.getId() == 0) {
			res = complain;
			final UserAccount user = LoginService.getPrincipal();
			final Customer customer = this.customerService.getCustomerUserAccount(user.getId());
			res.setCustomer(customer);
			this.validator.validate(res, binding);
		} else {
			res = this.complainRepository.findOne(complain.getId());
			final Complain c = new Complain();
			c.setId(res.getId());
			c.setVersion(res.getVersion());
			c.setDescription(complain.getDescription());
			c.setCustomer(res.getCustomer());
			c.setCashOrder(res.getCashOrder());
			this.validator.validate(c, binding);

			if (binding.hasErrors())
				throw new ValidationException();

			res = c;
		}
		return res;
	}

	public Collection<Complain> getComplainsByCustomer(final int customerId) {
		return this.complainRepository.getComplainByCustomer(customerId);
	}

	public Double ratioOfRestaurantsWithComplain() {
		return this.complainRepository.ratioOfRestaurantsWithComplain();
	}

}
