/*
 * /*
 * DomainEntity.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import domain.DomainEntity;

public class RegistrationFormCreditCard extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public RegistrationFormCreditCard() {
		super();
	}


	// Properties -------------------------------------------------------------

	private String	holderName;
	private String	brandName;
	private int		number;
	private int		expirationMonth;
	private int		expirationYear;
	private int		CW;


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
	@NotBlank
	public int getNumber() {
		return this.number;
	}

	public void setNumber(final int number) {
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

	public RegistrationFormCreditCard createCreditCard() {

		final RegistrationFormCreditCard registrationForm = new RegistrationFormCreditCard();

		registrationForm.setBrandName("");
		registrationForm.setHolderName("");
		registrationForm.setNumber(0);
		registrationForm.setExpirationMonth(0);
		registrationForm.setExpirationYear(0);
		registrationForm.setCW(0);

		return registrationForm;
	}

}
