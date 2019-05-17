
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity {

	private String		title;
	private int			totalPrice;
	private Restaurant	restaurant;


	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@Min(0)
	public int getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final int totalPrice) {
		this.totalPrice = totalPrice;
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

}
