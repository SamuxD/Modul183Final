package main.java.com.m183.modul183.services;

import main.java.com.m183.modul183.dao.PersonDAO;
import main.java.com.m183.modul183.person.Person;
import main.java.com.m183.modul183.person.PersonFrontend;
import main.java.com.m183.modul183.util.AESencrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Path("person")
public class PersonService {

    PersonDAO personDAO;
    Logger logger;

    public PersonService() {
        personDAO = new PersonDAO();
        logger = Logger.getLogger(getClass().getName());
    }

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("email") String email,
            @FormParam("password") String password
    ) {

        int httpStatus;
        String token = "";
        int personId = personDAO.authenticateByPassword(email, password);
        if (personId == -1) {
            httpStatus = 401;
        } else {
            httpStatus = 200;
            token = AESencrypt.encrypt(Integer.toString(personId));
        }
        NewCookie authCookie = new NewCookie(
                "authToken", token,
                "/", "", "Authorization", 6000, false
        );

        return Response
                .status(httpStatus)
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .cookie(authCookie)
                .build();
    }

    @POST
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPersons(
            @CookieParam("authToken") String token
    ) {
        int status = 401;
        NewCookie authCookie = new NewCookie(
                "authToken", token,
                "/", "", "Authorization", 6000, false
        );
        List<PersonFrontend> entity = new ArrayList();
        try {
            if (personDAO.authenticateById(AESencrypt.decrypt(token))) {
                status = 200;
                List<Person> users = personDAO.selectAllPersons();
                for (Person user : users) {
                    PersonFrontend userF = new PersonFrontend(user.getPersonId(), user.getSex(), user.getFirstname(), user.getLastname(), user.getEmail());
                    entity.add(userF);
                }
            }
        } catch (Exception e) {
            status = 500;
        }



        return Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity(entity)
                .cookie(authCookie)
                .build();
    }

    @POST
    @Path("save")
    @Produces(MediaType.TEXT_PLAIN)
    public Response savePerson(
            @FormParam("firstname") String firstname,
            @FormParam("lastname") String lastname,
            @FormParam("email") String email,
            @FormParam("password") String password,
            @FormParam("sex") boolean sex,
            @CookieParam("authToken") String token
    ) {
        NewCookie authCookie = new NewCookie(
                "authToken", token,
                "/", "", "Authorization", 6000, false
        );
        int status = 401;
        try {
            if (personDAO.authenticateById(AESencrypt.decrypt(token))) {
                if (personDAO.createPerson(firstname, lastname, email, password, sex)) {
                    status = 200;
                } else {
                    status = 501;
                }
            }
        } catch (Exception e) {
            status = 500;
        }

        return Response
                .status(status)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .cookie(authCookie)
                .build();
    }

    @POST
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout(
    ) {
        NewCookie authCookie = new NewCookie(
                "authToken", "-1",
                "/", "", "Authorization", 1, false
        );
        return Response
                .status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Credentials", "true")
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .cookie(authCookie)
                .build();
    }
}
