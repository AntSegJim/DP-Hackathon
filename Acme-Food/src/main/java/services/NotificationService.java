
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NotificationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Notification;

@Service
@Transactional
public class NotificationService {

	@Autowired
	private NotificationRepository	notificationRepository;


	public Notification create() {

		final Notification notification = new Notification();
		notification.setSubject("");
		notification.setBody("");

		return notification;

	}

	public Collection<Notification> findAll() {
		return this.notificationRepository.findAll();
	}

	public Notification findOne(final Integer id) {
		return this.notificationRepository.findOne(id);
	}

	public Notification save(final Notification n) {

		final UserAccount user = LoginService.getPrincipal();
		Assert.notNull(user.getAuthorities().iterator().next().getAuthority().equals("ADMIN"));

		Notification res;

		Assert.notNull(n.getBody() != null && n.getBody() != "", "No debe tener un cuerpo vacio");
		Assert.notNull(n.getSubject() != null && n.getSubject() != "", "No debe tener un subject vacio");

		res = this.notificationRepository.save(n);

		return res;

	}

}
