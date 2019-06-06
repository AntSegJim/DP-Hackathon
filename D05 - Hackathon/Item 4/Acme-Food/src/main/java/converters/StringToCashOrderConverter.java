
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CashOrderRepository;
import domain.CashOrder;

@Component
@Transactional
public class StringToCashOrderConverter implements Converter<String, CashOrder> {

	@Autowired
	CashOrderRepository	cashOrderRepository;


	@Override
	public CashOrder convert(final String text) {
		CashOrder result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.cashOrderRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
