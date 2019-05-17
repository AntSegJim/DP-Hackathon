
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Rating extends DomainEntity {

	private int			valoration;
	private String		comment;
	private Customer	customer;
	private Restaurant	restaurant;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
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

	@Range(min = 0, max = 10)
	public int getValoration() {
		return this.valoration;
	}

	public void setValoration(final int valoration) {
		this.valoration = valoration;
	}

	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

}
