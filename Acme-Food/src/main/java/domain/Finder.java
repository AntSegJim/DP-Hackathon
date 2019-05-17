
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private String					nameRestaurant;
	private String					speciality;
	private Collection<Restaurant>	restaurants;


	@ManyToMany
	@Valid
	public Collection<Restaurant> getRestaurants() {
		return this.restaurants;
	}

	public void setRestaurants(final Collection<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getNameRestaurant() {
		return this.nameRestaurant;
	}

	public void setNameRestaurant(final String nameRestaurant) {
		this.nameRestaurant = nameRestaurant;
	}

	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(final String speciality) {
		this.speciality = speciality;
	}

}
