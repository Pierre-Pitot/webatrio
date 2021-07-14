package webatrio;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Person {
	private Long id;
	private String lastName;
	private String firstName;
	private Date birthDate;
	private int age;

	public Person() {
		// Public constructor
	}

	public Person(String lastName, String firstName, Date birthDate) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	public Person(long id, String lastName, String firstName, Date birthDate) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		LocalDate today = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate birth = Instant.ofEpochMilli(this.birthDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
		Period d = Period.between(today, birth);
		return d.getYears();
	}
}
