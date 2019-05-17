
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import security.UserAccount;
import security.UserAccountRepository;

@Component
@Transactional
public class StringToUserAccountConverter implements Converter<String, UserAccount> {

	@Autowired
	private UserAccountRepository	UserAccountRepository;


	@Override
	public UserAccount convert(final String source) {

		UserAccount UserAccount;
		int id;

		try {
			if (StringUtils.isEmpty(source))
				UserAccount = null;
			else {
				id = Integer.valueOf(source);
				UserAccount = this.UserAccountRepository.findOne(id);
			}

		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return UserAccount;
	}

}
