
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	@Autowired
	private CreditCardRepository	creditCardRepository;

	@Autowired
	private AdministratorService	adminService;

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

}
