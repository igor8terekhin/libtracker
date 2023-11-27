package com.itxn.libratrack.dao;

import com.itxn.libratrack.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/libratrack";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private List<Person> people;


    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM Person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setFullName(resultSet.getString("fullName"));
                person.setBirthYear(resultSet.getInt("birthYear"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return people;
    }


    public void create(Person person) {
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO person VALUES(?, ?, ?)");
            preparedStatement.setInt(1, PEOPLE_COUNT++);
            preparedStatement.setString(2, person.getFullName());
            preparedStatement.setInt(3, person.getBirthYear());

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setFullName(resultSet.getString("fullName"));
            person.setBirthYear(resultSet.getInt("birthYear"));
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return person;
    }
}
