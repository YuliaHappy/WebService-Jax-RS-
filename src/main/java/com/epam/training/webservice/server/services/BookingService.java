package com.epam.training.webservice.server.services;

import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.common.exceptions.BookingException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/")
public class BookingService {
    private TicketService ticketService = TicketService.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/bookTicket/{idTicket}")
    @Produces(MediaType.APPLICATION_XML)
    public Ticket bookedTicket(@PathParam("idTicket") int idTicket, Person person) {
        Ticket ticket = ticketService.saveToSystem(idTicket, person);
        ticketService.putTicket(ticket);
        return ticket;
    }

    @GET
    @Path("/getByNumber/{numberTicket}")
    @Produces(MediaType.APPLICATION_XML)
    public Ticket getByNumber(@PathParam("numberTicket") int numberTicket) {
        return ticketService.getByNumber(numberTicket);
    }

    @GET
    @Path("/buyTicket/{numberTicket}")
    public void buyTicket(@PathParam("numberTicket") int numberTicket) {
        try {
            ticketService.buyTicket(numberTicket);
        } catch (BookingException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(e.getMessage())
                            .type(MediaType.TEXT_PLAIN)
                            .build());
        }
    }

    @GET
    @Path("/returnTicket/{numberTicket}")
    public void returnTicket(@PathParam("numberTicket") int numberTicket) {
        try {
            ticketService.returnTicket(numberTicket);
        } catch (BookingException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity(e.getMessage())
                            .type(MediaType.TEXT_PLAIN)
                            .build());
        }
    }

    @GET
    @Path("/getAllFree")
    @Produces(MediaType.APPLICATION_XML)
    public List<Ticket> getAllFree() {
        return ticketService.getAll();
    }

    @GET
    @Path("/getAllInSystem")
    @Produces(MediaType.APPLICATION_XML)
    public List<Ticket> getAllInSystem() {
        return ticketService.getAllInSystem();
    }
}
