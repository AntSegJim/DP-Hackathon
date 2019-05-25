
package controllers;

import java.util.Collection;
import java.util.HashSet;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import security.LoginService;
import services.ActorService;
import services.CustomerService;
import services.RestaurantService;
import domain.Actor;
import domain.Complain;
import domain.Customer;
import domain.Rating;
import domain.Restaurant;

@Controller
@RequestMapping("/export")
public class JSONController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private RestaurantService	restaurantService;


	@RequestMapping(value = "/json", method = RequestMethod.GET)
	public ResponseEntity<String> getMyJSONProfile() {
		ResponseEntity<String> res;
		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			final Customer h = this.customerService.getCustomerUserAccount(id);
			final Restaurant restaurant = this.restaurantService.getRestaurantByUserAccount(id);
			String json;
			if (this.customerService.getCustomerUserAccount(id) != null) {
				final Collection<Restaurant> restaurants = h.getFinder().getRestaurants();
				h.getFinder().setRestaurants(new HashSet<Restaurant>());

				final Collection<Rating> ratings = h.getRatings();
				h.setRatings(new HashSet<Rating>());

				final Collection<Complain> complains = h.getComplains();
				h.setComplains(new HashSet<Complain>());

				json = ow.writeValueAsString(h);

				h.getFinder().setRestaurants(restaurants);
				h.setRatings(ratings);
				h.setComplains(complains);

			} else if (this.restaurantService.getRestaurantByUserAccount(id) != null) {
				final Collection<Rating> ratings = restaurant.getRatings();
				restaurant.setRatings(new HashSet<Rating>());
				json = ow.writeValueAsString(restaurant);
				restaurant.setRatings(ratings);
			} else
				json = ow.writeValueAsString(a);
			res = new ResponseEntity<String>(json, HttpStatus.OK);
		} catch (final Exception e) {
			res = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return res;
	}

	public String JSON() {
		String res;
		try {
			final int id = LoginService.getPrincipal().getId();
			final Actor a = this.actorService.getActorByUserAccount(id);
			final ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			final String json = ow.writeValueAsString(a);
			res = json;
		} catch (final Exception e) {
			res = "Error";
		}
		return res;
	}
}
