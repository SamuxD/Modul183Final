package main.java.com.m183.modul183.dao;


import main.java.com.m183.modul183.dao.factory.ConnectionFactory;
import main.java.com.m183.modul183.person.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PersonDAO {

    public boolean authenticateById(String stringId) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            int intId = Integer.parseInt(stringId);
            PreparedStatement pstmt = conn.prepareStatement("SELECT personId from person where personID = ?");
            pstmt.setInt(1, intId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int authenticateByPassword(String email, String password) {
        Connection conn = ConnectionFactory.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT personId from person where email = ? AND password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                return rs.getInt("personId");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return -1;
    }


    public List<Person> selectAllPersons() {
        Connection conn = ConnectionFactory.getConnection();
        List<Person> personList = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT personId, sex, firstName, lastName, password, email from person");
            ResultSet rs = pstmt.executeQuery();
            personList = new ArrayList<>();
            while (rs.next()) {
                Person p = new Person();
                p.setPersonId(rs.getInt("personId"));
                p.setSex(rs.getBoolean("sex"));
                p.setFirstname(rs.getString("firstName"));
                p.setLastname(rs.getString("lastName"));
                p.setPassword(rs.getString("password"));
                p.setEmail(rs.getString("email"));
                personList.add(p);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return personList;
    }

    public Person selectPersonById(int personId) {
        Connection conn = ConnectionFactory.getConnection();
        Person person = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT personId, sex, firstName, lastName, password, email from person where personId=?");
            pstmt.setInt(1, personId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                person = new Person();
                person.setPersonId(rs.getInt("personId"));
                person.setSex(rs.getBoolean("sex"));
                person.setFirstname(rs.getString("firstName"));
                person.setLastname(rs.getString("lastName"));
                person.setPassword(rs.getString("password"));
                person.setEmail(rs.getString("email"));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return person;
    }


    public boolean createPerson(String firstname, String lastname, String email, String password, boolean sex) {
        Connection conn = ConnectionFactory.getConnection();
        int state = 0;
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO person (sex, firstName, lastName, email, password) VALUES (?, ?, ?, ?, ?)");
            pstmt.setBoolean(1, sex);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, email);
            pstmt.setString(5, password);
            state = pstmt.executeUpdate();
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}

