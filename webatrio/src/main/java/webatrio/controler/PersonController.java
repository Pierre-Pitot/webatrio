package webatrio.controler;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import webatrio.Person;
import webatrio.db.DBConnection;

@RestController
public class PersonController {
	@GetMapping("/person")
	public List<Person> person(@RequestParam(value = "name") String name) throws SQLException {
		return DBConnection.getInstance().queryPersons();
	}

	@PostMapping("/person")
	public void person(@RequestParam(value = "lastName") String lastName, @RequestParam(value = "firstName") String firstName, @RequestParam(value = "birthDate") Date birthDate)
			throws SQLException {
		DBConnection.getInstance().insertPerson(new Person(lastName, firstName, birthDate));
	}
}
