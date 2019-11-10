package main.java.com.m183.modul183.person;

public class PersonFrontend {

    private int personId;
    private boolean sex;
    private String firstname;
    private String lastname;
    private String email;

    public PersonFrontend(int personId, boolean sex, String firstname, String lastname, String email) {
        this.personId = personId;
        this.sex = sex;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
