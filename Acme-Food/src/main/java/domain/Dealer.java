
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Dealer extends Actor {

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

}
