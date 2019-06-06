
package forms;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.DomainEntity;

public class PersonalDataForm extends DomainEntity {

	public PersonalDataForm() {
		super();
	}


	private String	fullName;
	private String	statement;
	private String	phoneNumber;
	private String	githubProfile;
	private String	linkedlnProfile;
	private Boolean	patternPhone;


	@NotNull
	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	@NotNull
	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	@NotNull
	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@NotNull
	@URL
	@NotBlank
	public String getGithubProfile() {
		return this.githubProfile;
	}

	public void setGithubProfile(final String githubProfile) {
		this.githubProfile = githubProfile;
	}

	@NotNull
	@URL
	@NotBlank
	public String getLinkedlnProfile() {
		return this.linkedlnProfile;
	}

	public void setLinkedlnProfile(final String linkedlnProfile) {
		this.linkedlnProfile = linkedlnProfile;
	}

	public Boolean getPatternPhone() {
		return this.patternPhone;
	}

	public void setPatternPhone(final Boolean patternPhone) {
		this.patternPhone = patternPhone;
	}

	public PersonalDataForm createPersonalData() {

		final PersonalDataForm registrationForm = new PersonalDataForm();
		registrationForm.setFullName("");
		registrationForm.setPatternPhone(false);
		registrationForm.setGithubProfile("");
		registrationForm.setLinkedlnProfile("");
		//registrationForm.setPhoneNumber("");
		registrationForm.setStatement("");

		return registrationForm;
	}

}
