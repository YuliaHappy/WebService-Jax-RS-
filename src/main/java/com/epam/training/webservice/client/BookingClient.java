package com.epam.training.webservice.client;

import com.epam.training.webservice.client.reporter.Reporter;
import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.Date;
import java.util.List;

public class BookingClient {
    private static final String ALL_FREE = "http://localhost:8000/RestBookingService/booking/getAllFree/";
    private static final String ALL_IN_SYSTEM = "http://localhost:8000/RestBookingService/booking/getAllInSystem/";
    private static final String BOOK_TICKET = "http://localhost:8000/RestBookingService/booking/bookTicket/";
    private static final String GET_BY_NUMBER = "http://localhost:8000/RestBookingService/booking/getByNumber/";
    private static final String BUY_TICKET = "http://localhost:8000/RestBookingService/booking/buyTicket/";
    private static final String RETURN_TICKET = "http://localhost:8000/RestBookingService/booking/returnTicket/";


    public static void main(String[] args) {
        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);

        List<Ticket> freeTickets = getAllFree(client);
        System.out.println(Reporter.getAllTicket(freeTickets, "ALL FREE"));

        Ticket bookTicket = bookTicket(client, 2, new Person("SSS", "dd", "hjd", new Date(2010, 10, 1)));
        freeTickets = getAllFree(client);
        List<Ticket> ticketsInSystem = getAllTicketInSystem(client);
        System.out.println(Reporter.bookedTicket(bookTicket, freeTickets, ticketsInSystem));

        bookTicket = getByNumber(client, bookTicket.getNumberBook());
        ticketsInSystem = getAllTicketInSystem(client);
        System.out.println(Reporter.getTicketByNumberBook(bookTicket, ticketsInSystem));

        System.out.println("True buy");
        boolean resultOperation = buyTicket(client, bookTicket.getNumberBook());
        ticketsInSystem = getAllTicketInSystem(client);
        System.out.println(Reporter.buyTicket(resultOperation, ticketsInSystem));
        System.out.println("Bad buy");
        resultOperation = buyTicket(client, ticketsInSystem.get(0).getNumberBook());
        ticketsInSystem = getAllTicketInSystem(client);
        System.out.println(Reporter.buyTicket(resultOperation, ticketsInSystem));

        System.out.println("Return ticket");
        resultOperation = returnTicket(client, ticketsInSystem.get(0).getNumberBook());
        ticketsInSystem = getAllTicketInSystem(client);
        freeTickets = getAllFree(client);
        System.out.println(Reporter.returnTicket(resultOperation, ticketsInSystem, freeTickets));

    }

    private static List<Ticket> getAllFree(Client client) {
        return client.target(ALL_FREE)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(new GenericType<List<Ticket>>(){});
    }

    private static List<Ticket> getAllTicketInSystem(Client client) {
        return client.target(ALL_IN_SYSTEM)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(new GenericType<List<Ticket>>(){});
    }

    private static Ticket bookTicket(Client client, int idTicket, Person person) {
          return client.target(BOOK_TICKET + idTicket)
                  .request(MediaType.APPLICATION_XML)
                  .buildPost(
                          Entity.entity(person, MediaType.APPLICATION_XML))
                  .invoke()
                  .readEntity(Ticket.class);
    }

    private static Ticket getByNumber(Client client, int numberTicket) {
        return client.target(GET_BY_NUMBER + numberTicket)
                .request(MediaType.APPLICATION_XML)
                .get()
                .readEntity(Ticket.class);
    }

    private static boolean buyTicket(Client client, int numberTicket) {
        return client.target(BUY_TICKET + numberTicket)
                .request()
                .get()
                .readEntity(Boolean.class);
    }

    private static boolean returnTicket(Client client, int numberTicket) {
        return client.target(RETURN_TICKET + numberTicket)
                .request()
                .get()
                .readEntity(Boolean.class);
    }
}
