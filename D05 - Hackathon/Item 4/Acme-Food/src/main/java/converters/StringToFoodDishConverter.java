
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FoodDishesRepository;
import domain.FoodDishes;

@Component
@Transactional
public class StringToFoodDishConverter implements Converter<String, FoodDishes> {

	@Autowired
	FoodDishesRepository	foodDishesReposiroty;


	@Override
	public FoodDishes convert(final String text) {
		FoodDishes result;
		int id;

		try {
			if (StringUtils.isEmpty(text))
				result = null;
			else {
				id = Integer.valueOf(text);
				result = this.foodDishesReposiroty.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
