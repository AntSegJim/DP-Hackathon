
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Complain;

@Component
@Transactional
public class ComplainToStringConverter implements Converter<Complain, String> {

	@Override
	public String convert(final Complain complain) {
		String result;

		if (complain == null)
			result = null;
		else
			result = String.valueOf(complain.getId());

		return result;
	}
}
