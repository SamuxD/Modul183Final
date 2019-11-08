package main.java.com.m183.modul183.services;


import main.java.com.m183.modul183.dao.PersonDAO;
import main.java.com.m183.modul183.util.AESencrypt;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("person")
@ApplicationPath("resource")
public class PersonService {

    PersonDAO personDAO;

    public PersonService () {
        personDAO = new PersonDAO();
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

            token = AESencrypt.decrypt(Integer.toString(personId));
        }
        NewCookie authCookie = new NewCookie(
                "authToken", token,
                "/", "", "Authorization", 6000, false
        );
        return Response
                .status(httpStatus)
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .cookie(authCookie)
                .build();
    }

    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {
        NewCookie authCookie = new NewCookie(
                "authToken", "",
                "/", "", "Authorization", 1, false
        );
        return Response
                .status(200)
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
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
            @CookieParam("authToken") String authToken
    ) {
        int httpStatus;

        if (personDAO.authenticateById(AESencrypt.decrypt(authToken))) {

            if (personDAO.createPerson(firstname, lastname, email, password, sex)) {
                httpStatus = 200;
            } else {
                httpStatus = 500;
            }
        } else {
            httpStatus = 403;
        }

        return Response
                .status(httpStatus)
                .header("Access-Control-Allow-Headers",
                        "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods",
                        "GET, POST, DELETE")
                .entity("")
                .build();
    }
}
