
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CashOrder;

@Component
@Transactional
public class CashOrderToStringConverter implements Converter<CashOrder, String> {

	@Override
	public String convert(final CashOrder cashOrder) {
		String result;

		if (cashOrder == null)
			result = null;
		else
			result = String.valueOf(cashOrder.getId());

		return result;
	}
}
