
package validators;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UrlCollectionValidator implements ConstraintValidator<URLCollection, Collection<String>> {

	@Override
	public boolean isValid(final Collection<String> urls, final ConstraintValidatorContext context) {
		boolean res = true;
		for (final String url : urls) {
			final String regexURL = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			final Pattern patternURL = Pattern.compile(regexURL);
			final Matcher matcherURL = patternURL.matcher(url);
			if (matcherURL.find() != true) {
				res = false;
				break;
			}
		}
		return res;
	}
	@Override
	public void initialize(final URLCollection arg0) {

	}

}
