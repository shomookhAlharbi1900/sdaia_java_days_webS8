package org.example;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.example.dto.EmployeeIdDto;

import java.time.LocalDate;

/**
 * Root resource (exposed at "myresource" path)
 */
@Singleton
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(
            @HeaderParam("apiKey") String apiKey,
            @CookieParam("username") String username,
            @Context HttpHeaders headers ,
            @Context UriInfo uriInfo
    ) {
        System.out.println(headers.getCookies());
        System.out.println(uriInfo.getAbsolutePath());

        return "Got it! name :" + username +"apiKey"+ apiKey;
    }



    @GET
    @Path("{day}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getDate(@PathParam("day") LocalDate day) {
        return "The date is: " + day;
    }



    @GET
    @Path("/employees/{empId}")
    @Produces(MediaType.APPLICATION_JSON)
    public EmployeeIdDto getDate(@PathParam("empId") EmployeeIdDto empId) {
        return empId;
    }

}
