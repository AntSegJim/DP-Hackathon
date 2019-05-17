/*
 * CustomizableSystemToStringConverter.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CustomizableSystem;

@Component
@Transactional
public class CustomizableSystemToStringConverter implements Converter<CustomizableSystem, String> {

	@Override
	public String convert(final CustomizableSystem customizableSystem) {
		String result;

		if (customizableSystem == null)
			result = null;
		else
			result = String.valueOf(customizableSystem.getId());

		return result;
	}

}
