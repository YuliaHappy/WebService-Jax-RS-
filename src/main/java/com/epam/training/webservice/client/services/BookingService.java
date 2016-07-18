package com.epam.training.webservice.client.services;

import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.common.exceptions.BookingException;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class BookingService {
    private Client client;
    private final String ALL_FREE = "http://localhost:8000/RestBookingService/booking/getAllFree/";
    private final String ALL_IN_SYSTEM = "http://localhost:8000/RestBookingService/booking/getAllInSystem/";
    private final String BOOK_TICKET = "http://localhost:8000/RestBookingService/booking/bookTicket/";
    private final String GET_BY_NUMBER = "http://localhost:8000/RestBookingService/booking/getByNumber/";
    private final String BUY_TICKET = "http://localhost:8000/RestBookingService/booking/buyTicket";
    private final String RETURN_TICKET = "http://localhost:8000/RestBookingService/booking/returnTicket/";

    public BookingService() {
        client = ClientBuilder.newClient(new ClientConfig());
    }

    public List<Ticket> getAllFree() {
        return client.target(ALL_FREE)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(new GenericType<List<Ticket>>(){});
    }

    public List<Ticket> getAllTicketInSystem() {
        return client.target(ALL_IN_SYSTEM)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(new GenericType<List<Ticket>>(){});
    }

    public Ticket bookTicket(int idTicket, Person person) {
        return client.target(BOOK_TICKET + idTicket)
                .request(MediaType.APPLICATION_XML)
                .buildPost(
                        Entity.entity(person, MediaType.APPLICATION_XML))
                .invoke()
                .readEntity(Ticket.class);
    }

    public Ticket getByNumber(int numberTicket) {
        return client.target(GET_BY_NUMBER + numberTicket)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(Ticket.class);
    }

    public Ticket buyTicket(Ticket ticket) throws BookingException {
        Response response = client.target(BUY_TICKET)
                .request()
                .put(Entity.entity(ticket, MediaType.APPLICATION_XML));
        if (response.getStatus() == 400) {
            throw new BookingException(response.readEntity(String.class));
        }
        return response.readEntity(Ticket.class);
    }

    public Ticket returnTicket(int numberTicket) throws BookingException {
        Response response = client.target(RETURN_TICKET + numberTicket)
                .request(MediaType.APPLICATION_XML)
                .delete();
        if (response.getStatus() == 400) {
            throw new BookingException(response.readEntity(String.class));
        }
        return response.readEntity(Ticket.class);
    }
}
