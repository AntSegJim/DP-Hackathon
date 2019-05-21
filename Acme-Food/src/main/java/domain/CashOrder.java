
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class CashOrder extends DomainEntity {

	private int						status;
	private int						draftMode;
	private Date					moment;
	private Double					totalPrice;
	private Date					senderMoment;
	private int						choice;
	private String					ticker;
	private Customer				customer;
	private Restaurant				restaurant;
	private Dealer					dealer;
	private Collection<FoodDishes>	foodDisheses;
	private Collection<Offer>		offers;


	@ManyToMany
	@Valid
	public Collection<Offer> getOffers() {
		return this.offers;
	}

	public void setOffers(final Collection<Offer> offers) {
		this.offers = offers;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(final Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@ManyToMany
	@Valid
	public Collection<FoodDishes> getFoodDisheses() {
		return this.foodDisheses;
	}

	public void setFoodDisheses(final Collection<FoodDishes> foodDisheses) {
		this.foodDisheses = foodDisheses;
	}

	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(optional = true)
	@Valid
	public Dealer getDealer() {
		return this.dealer;
	}

	public void setDealer(final Dealer dealer) {
		this.dealer = dealer;
	}

	@Range(min = 0, max = 3)
	public int getStatus() {
		return this.status;
	}

	public void setStatus(final int status) {
		this.status = status;
	}

	@Range(min = 0, max = 1)
	public int getDraftMode() {
		return this.draftMode;
	}

	public void setDraftMode(final int draftMode) {
		this.draftMode = draftMode;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Min(0)
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Future
	public Date getSenderMoment() {
		return this.senderMoment;
	}

	public void setSenderMoment(final Date senderMoment) {
		this.senderMoment = senderMoment;
	}

	@Range(min = 0, max = 1)
	public int getChoice() {
		return this.choice;
	}

	public void setChoice(final int choice) {
		this.choice = choice;
	}

	@Pattern(regexp = "^[A-z]{4}\\-[0-9]{6}$")
	@Column(unique = true)
	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}
