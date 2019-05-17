/*
 * WelcomeController.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CustomizableSystemService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private CustomizableSystemService	customizableService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index(final HttpServletRequest request) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;
		String language;
		String urlBanner;
		final String nameApp;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());
		language = LocaleContextHolder.getLocale().getLanguage();
		urlBanner = this.customizableService.getUrlBanner();
		nameApp = this.customizableService.getNameApp();

		request.getSession().setAttribute("urlBanner", urlBanner);
		request.getSession().setAttribute("nameApp", nameApp);

		result = new ModelAndView("welcome/index");
		if (language.equals("en"))
			result.addObject("name", this.customizableService.getWelcomeMessage());
		else
			result.addObject("name", this.customizableService.getSpanishWelcomeMessage());
		result.addObject("moment", moment);

		return result;
	}
}
