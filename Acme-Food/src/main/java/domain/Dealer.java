
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Dealer extends Actor {

	private int			isDealing;
	private Restaurant	restaurant;


	@ManyToOne(optional = false)
	@Valid
	@NotNull
	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(final Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@Range(min = 0, max = 1)
	public int getIsDealing() {
		return this.isDealing;
	}

	public void setIsDealing(final int isDealing) {
		this.isDealing = isDealing;
	}

}
