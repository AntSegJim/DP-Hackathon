
package services;

import java.util.Calendar;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.CreditCard;
import domain.Customer;
import forms.RegistrationFormCustomerAndCreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;


	public Collection<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}
	public CreditCard findOne(final Integer creditCardId) {
		return this.creditCardRepository.findOne(creditCardId);
	}
	//Metodo create
	public CreditCard create() {
		final CreditCard cc = new CreditCard();
		cc.setBrandName("");
		cc.setHolderName("");
		cc.setNumber("");
		cc.setExpirationMonth(0);
		cc.setExpirationYear(0);
		cc.setCW(0);
		return cc;
	}
	public CreditCard save(final CreditCard cc) {
		final Collection<String> creditCardsNumbers = this.getAllNumbers();

		if (cc.getId() != 0) {

			final CreditCard creditCard = this.findOne(cc.getId());
			final String number = creditCard.getNumber();
			creditCardsNumbers.remove(number);
		}
		Assert.isTrue(!creditCardsNumbers.contains(cc.getNumber()));

		Assert.isTrue(cc != null && cc.getBrandName() != null && cc.getHolderName() != null && cc.getBrandName() != "" && cc.getHolderName() != "");
		return this.creditCardRepository.save(cc);

	}

	public void delete(final CreditCard creditCard) {
		this.creditCardRepository.delete(creditCard);
	}

	public Collection<String> getAllNumbers() {
		return this.creditCardRepository.getAllNumbercreditCards();
	}

	public CreditCard getCreditCardByNumber(final String number) {
		return this.creditCardRepository.CreditCardByNumber(number);
	}

	public CreditCard reconstruct(final RegistrationFormCustomerAndCreditCard registrationForm, final BindingResult binding) {
		CreditCard res = new CreditCard();

		if (registrationForm.getId() == 0) {
			res.setId(registrationForm.getId());
			res.setVersion(registrationForm.getVersion());
			res.setBrandName(registrationForm.getBrandName());
			res.setHolderName(registrationForm.getHolderName());
			res.setNumber(registrationForm.getNumber());
			res.setExpirationMonth(registrationForm.getExpirationMonth());
			res.setExpirationYear(registrationForm.getExpirationYear());
			res.setCW(registrationForm.getCW());

			final Collection<String> creditCardsNumbers = this.getAllNumbers();
			if (creditCardsNumbers.contains(res.getNumber()))
				binding.rejectValue("number", "NumeroRepetido");

			final Calendar cal = Calendar.getInstance();
			final int a�oActual = cal.get(Calendar.YEAR);
			final int mesActual = cal.get(Calendar.MONTH);

			if (res.getExpirationYear() < a�oActual || (res.getExpirationMonth() <= mesActual && res.getExpirationYear() == a�oActual))
				binding.rejectValue("expirationYear", "FechaNoValida");

			this.validator.validate(res, binding);

		} else {
			Customer customer;
			customer = this.customerService.findOne(registrationForm.getId());
			res = customer.getCreditCard();
			final CreditCard p = new CreditCard();
			p.setId(res.getId());
			p.setVersion(res.getVersion());
			p.setBrandName(registrationForm.getBrandName());
			p.setHolderName(registrationForm.getHolderName());
			p.setNumber(registrationForm.getNumber());
			p.setExpirationMonth(registrationForm.getExpirationMonth());
			p.setExpirationYear(registrationForm.getExpirationYear());
			p.setCW(registrationForm.getCW());

			final Collection<String> creditCardsNumbers = this.getAllNumbers();
			final CreditCard creditCard = this.findOne(res.getId());
			final String number = creditCard.getNumber();
			creditCardsNumbers.remove(number);
			if (creditCardsNumbers.contains(p.getNumber()))
				binding.rejectValue("number", "NumeroRepetido");

			final Calendar cal = Calendar.getInstance();
			final int a�oActual = cal.get(Calendar.YEAR);
			final int mesActual = cal.get(Calendar.MONTH);
			if (p.getExpirationYear() < a�oActual || (p.getExpirationMonth() <= mesActual && p.getExpirationYear() == a�oActual))
				binding.rejectValue("expirationYear", "FechaNoValida");

			this.validator.validate(p, binding);
			res = p;

		}
		return res;
	}

}
