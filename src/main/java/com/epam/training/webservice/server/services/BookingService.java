package com.epam.training.webservice.server.services;

import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/")
public class BookingService {
    private TicketService ticketService = TicketService.getInstance();

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/bookTicket/{idTicket}")
    @Produces(MediaType.APPLICATION_XML)
    public Ticket bookedTicket(@PathParam("idTicket") String idTicket, Person person) {
        Ticket ticket = ticketService.saveToSystem(Integer.parseInt(idTicket), person);
        ticketService.putTicket(ticket);
        return ticket;
    }

    @GET
    @Path("/getByNumber/{numberTicket}")
    @Produces(MediaType.APPLICATION_XML)
    public Ticket getByNumber(@PathParam("numberTicket") String numberTicket) {
        return ticketService.getByNumber(Integer.parseInt(numberTicket));
    }

    @GET
    @Path("/buyTicket/{numberTicket}")
    @Produces("text/plain")
    public String buyTicket(@PathParam("numberTicket") String numberTicket) {
        return String.valueOf(ticketService.buyTicket(Integer.parseInt(numberTicket)));
    }

    @GET
    @Path("/returnTicket/{numberTicket}")
    @Produces("text/plain")
    public String returnTicket(@PathParam("numberTicket") String numberTicket) {
        return String.valueOf(ticketService.returnTicket(Integer.parseInt(numberTicket)));
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
