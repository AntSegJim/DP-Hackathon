
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	private String					keyWord;
	private Integer					minScore;
	private Integer					maxScore;
	private Collection<Restaurant>	restaurants;
	private Date					moment;


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

	@Range(min = 0, max = 10)
	public Integer getMinScore() {
		return this.minScore;
	}

	public void setMinScore(final Integer minScore) {
		this.minScore = minScore;
	}

	@Range(min = 0, max = 10)
	public Integer getMaxScore() {
		return this.maxScore;
	}

	public void setMaxScore(final Integer maxScore) {
		this.maxScore = maxScore;
	}

	@NotNull
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

}
