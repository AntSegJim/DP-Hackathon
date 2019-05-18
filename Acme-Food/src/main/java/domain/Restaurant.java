
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Restaurant extends Actor {

	private Collection<Rating>	ratings;
	private String				comercialName;
	private String				speciality;
	private int					isBanned;
	private Integer				mediumScore;


	@Length(min = 4)
	@NotBlank
	@NotNull
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getComercialName() {
		return this.comercialName;
	}

	public void setComercialName(final String comercialName) {
		this.comercialName = comercialName;
	}

	@NotBlank
	@NotNull
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(final String speciality) {
		this.speciality = speciality;
	}

	@Range(min = 0, max = 1)
	public int getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final int isBanned) {
		this.isBanned = isBanned;
	}

	@NotNull
	public Integer getMediumScore() {
		return this.mediumScore;
	}

	public void setMediumScore(final Integer mediumScore) {
		this.mediumScore = mediumScore;
	}

	@OneToMany
	@Valid
	public Collection<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(final Collection<Rating> ratings) {
		this.ratings = ratings;
	}

}
