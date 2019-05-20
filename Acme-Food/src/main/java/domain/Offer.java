
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Offer extends DomainEntity {

	private String					title;
	private Double					totalPrice;
	private Restaurant				restaurant;
	private Collection<FoodDishes>	foodDisheses;


	@ManyToMany
	@Valid
	public Collection<FoodDishes> getFoodDisheses() {
		return this.foodDisheses;
	}

	public void setFoodDisheses(final Collection<FoodDishes> foodDisheses) {
		this.foodDisheses = foodDisheses;
	}

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
	public Double getTotalPrice() {
		return this.totalPrice;
	}

	public void setTotalPrice(final Double totalPrice) {
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
