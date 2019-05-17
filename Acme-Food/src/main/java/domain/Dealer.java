
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Dealer extends Actor {

	private int	isDealing;


	@Range(min = 0, max = 1)
	public int getIsDealing() {
		return this.isDealing;
	}

	public void setIsDealing(final int isDealing) {
		this.isDealing = isDealing;
	}

}
