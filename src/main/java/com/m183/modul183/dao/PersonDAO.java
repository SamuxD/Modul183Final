package main.java.com.m183.modul183.dao;


import main.java.com.m183.modul183.dao.factory.ConnectionFactory;
import main.java.com.m183.modul183.person.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class PersonDAO {

    //private final Logger LOGGER = LoggerFactory.getLogger(getClass());

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
            PreparedStatement pstmt = conn.prepareStatement("SELECT personId from person where email = email AND password = password");
            //pstmt.setString(1, email);
            //pstmt.setString(2, password);
            Statement stmt=conn.createStatement();
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                //LOGGER.info("User logged in succesfully");
                return rs.getInt("personId");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        //LOGGER.info("User Loggin Failed with credentials email:" + email + "and password:" + password);
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
            //LOGGER.info("new User with email:" + email + "and password:" + password + "has been created");
            return true;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }
}

