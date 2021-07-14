package webatrio.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import webatrio.Person;

public class DBConnection {
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:~/test";

	// Database credentials
	private static final String USER = "sa";
	private static final String PASS = "";

	private Connection conn;
	private static DBConnection instance;

	private DBConnection() {
		conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			conn.setAutoCommit(false);
		} catch (Exception e) {
			// TODO Log
			e.printStackTrace();
		}
	}

	public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();

			// Dirty init database
			try {
				instance.queryPersons();
			} catch (SQLException e) {
				try {
					instance.initDB();
				} catch (SQLException e1) {
					// TODO Log error
					e1.printStackTrace();
				}
			}
		}
		return instance;
	}

	private void initDB() throws SQLException {
		String query = "CREATE TABLE PERSON (	ID BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,	LASTNAME VARCHAR2(100),	FIRSTNAME VARCHAR2(100),	BIRTHDATE DATE)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.executeUpdate();
		conn.commit();
	}

	public List<Person> queryPersons() throws SQLException {
		String query = "SELECT FROM PERSON ORDER BY LASTNAME";
		PreparedStatement statement = conn.prepareStatement(query);

		ResultSet rs = statement.executeQuery();
		List<Person> matches = new ArrayList<Person>();
		while (rs.next()) {
			matches.add(new Person(rs.getLong("ID"), rs.getString("LASTNAME"), rs.getString("FIRSTNAME"), new Date(rs.getDate("BIRTHDATE").getTime())));
		}
		return matches;
	}

	public void insertPerson(Person person) throws SQLException {
		String query = "INSERT INTO PERSON(LASTNAME, FIRSTNAME, BIRTHDATE) VALUES (?, ?, ?)";
		PreparedStatement statement = conn.prepareStatement(query);
		int i = 1;
		statement.setString(i++, person.getLastName());
		statement.setString(i++, person.getFirstName());
		statement.setDate(i++, new java.sql.Date(person.getBirthDate().getTime()));

		statement.executeUpdate();
		conn.commit();
	}

	public void updatePerson(Person person) throws SQLException {
		String query = "UPDATE PERSON SET LASTNAME = ?, FIRSTNAME = ?, BIRTHDATE = ? WHERE ID = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		int i = 1;
		statement.setString(i++, person.getLastName());
		statement.setString(i++, person.getFirstName());
		statement.setDate(i++, new java.sql.Date(person.getBirthDate().getTime()));
		statement.setLong(i++, person.getId());

		statement.executeUpdate();
		conn.commit();
	}

	public void deletePerson(Person person) throws SQLException {
		String query = "DELETE FROM PERSON WHERE ID = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setLong(1, person.getId());

		statement.executeUpdate();
		conn.commit();
	}
}
