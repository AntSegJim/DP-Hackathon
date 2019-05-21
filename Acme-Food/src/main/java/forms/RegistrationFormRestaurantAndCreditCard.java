/*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;

import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Rating;

public class RegistrationFormRestaurantAndCreditCard extends Actor {

	// Constructors -----------------------------------------------------------

	public RegistrationFormRestaurantAndCreditCard() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String				password;

	private Boolean				check;

	private Boolean				patternPhone;

	private Collection<Rating>	ratings;
	private String				comercialName;
	private String				speciality;
	private int					isBanned;
	private Integer				mediumScore;


	// Business methods -------------------------------------------------------

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

	public Collection<Rating> getRatings() {
		return this.ratings;
	}

	public void setRatings(final Collection<Rating> ratings) {
		this.ratings = ratings;
	}

	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	public Boolean getCheck() {
		return this.check;
	}

	public void setCheck(final Boolean check) {
		this.check = check;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	// Business methods -------------------------------------------------------

	public RegistrationFormRestaurantAndCreditCard createToCompanyAndCreditCard() {

		final RegistrationFormRestaurantAndCreditCard registrationForm = new RegistrationFormRestaurantAndCreditCard();
		registrationForm.setCheck(false);
		registrationForm.setPatternPhone(false);
		registrationForm.setAddress("");
		registrationForm.setEmail("");
		registrationForm.setName("");
		registrationForm.setVatNumber("");
		registrationForm.setPhoto("");
		registrationForm.setSurnames("");
		registrationForm.setAddress("");

		registrationForm.setRatings(new HashSet<Rating>());
		registrationForm.setComercialName("");
		registrationForm.setSpeciality("");
		registrationForm.setIsBanned(0);
		registrationForm.setMediumScore(0);

		//PREGUNTAR
		final UserAccount user = new UserAccount();
		user.setAuthorities(new HashSet<Authority>());
		final Authority ad = new Authority();
		//ad.setAuthority(Authority.RESTAURANT);
		user.getAuthorities().add(ad);

		//NUEVO
		user.setUsername("");
		user.setPassword("");

		registrationForm.setUserAccount(user);

		return registrationForm;
	}

}
