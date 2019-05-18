
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FoodDishes;

@Component
@Transactional
public class FoodDishToStringConverter implements Converter<FoodDishes, String> {

	@Override
	public String convert(final FoodDishes foodDish) {
		String result;

		if (foodDish == null)
			result = null;
		else
			result = String.valueOf(foodDish.getId());

		return result;
	}
}
