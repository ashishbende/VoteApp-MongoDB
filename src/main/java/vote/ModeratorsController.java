package vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/*
 *  @author Ashish Bende
 */

@RestController
@RequestMapping("/api/v1")
@Repository
@ComponentScan
public class ModeratorsController {

	@Autowired
	private ModeratorsRepository modrepo;
	private final AtomicLong counter = new AtomicLong(123455);

	DateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.ms'Z'");
	String currentTime = dt.format(new Date());

	//ArrayList<Moderators> modlist = new ArrayList<Moderators>();

	@RequestMapping(value = "/moderators", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Moderators moderatorsCreate(@RequestBody @Validated(Checker.CheckerAll.class) Moderators moderators) {

		String name = moderators.getName();
		String email = moderators.getEmail();
		String password = moderators.getPassword();

		Moderators mods = new Moderators(String.valueOf(counter.incrementAndGet()), name, email, password, currentTime);
		return modrepo.save(mods);
		//modlist.add(mods);
		//return mods;

	}

	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.GET)
	public Moderators moderatorsView(@PathVariable("id") String id) {

		Moderators moderators = modrepo.findOne(id);
		return moderators;

	}

	@RequestMapping(value = "/moderators/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	public Moderators moderatorsUpdate(@PathVariable("id") String id, @RequestBody @Validated(Checker.CheckerFields.class) Moderators moderators) {

		Moderators update = modrepo.findOne(id);
		update.setEmail(moderators.getEmail());
		update.setPassword(moderators.getPassword());
		return modrepo.save(update);
	}
}

