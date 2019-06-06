
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Access(AccessType.PROPERTY)
public class Complain extends DomainEntity {

	private String		description;
	private Customer	customer;
	private CashOrder	cashOrder;


	@NotNull
	@NotBlank
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	public CashOrder getCashOrder() {
		return this.cashOrder;
	}

	public void setCashOrder(final CashOrder cashOrder) {
		this.cashOrder = cashOrder;
	}

}
