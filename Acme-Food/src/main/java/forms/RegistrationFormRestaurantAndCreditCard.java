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
import domain.CreditCard;
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

	private String				holderName;
	private String				brandName;
	private String				number;
	private int					expirationMonth;
	private int					expirationYear;
	private int					CW;

	private Collection<Rating>	ratings;
	private String				comercialName;
	private String				speciality;
	private int					isBanned;
	private Integer				mediumScore;


	// Business methods -------------------------------------------------------

	@NotBlank
	@NotNull
	public String getHolderName() {
		return this.holderName;
	}

	public void setHolderName(final String holderName) {
		this.holderName = holderName;
	}

	@NotBlank
	@NotNull
	public String getBrandName() {
		return this.brandName;
	}

	public void setBrandName(final String brandName) {
		this.brandName = brandName;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}
	@NotNull
	public int getExpirationMonth() {
		return this.expirationMonth;
	}

	public void setExpirationMonth(final int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	@NotNull
	public int getExpirationYear() {
		return this.expirationYear;
	}

	public void setExpirationYear(final int expirationYear) {
		this.expirationYear = expirationYear;
	}
	@NotNull
	@Range(min = 100, max = 999)
	public int getCW() {
		return this.CW;
	}

	public void setCW(final int cW) {
		this.CW = cW;
	}

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
		registrationForm.setCreditCard(new CreditCard());
		registrationForm.setPassword("");
		registrationForm.setBrandName("");
		registrationForm.setHolderName("");
		registrationForm.setNumber("");
		registrationForm.setExpirationMonth(0);
		registrationForm.setExpirationYear(0);
		registrationForm.setCW(0);
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
