package com.epam.training.webservice.client;

import com.epam.training.webservice.client.reporter.Reporter;
import com.epam.training.webservice.client.services.BookingService;
import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.common.exceptions.BookingException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookingClient {
    public static void main(String[] args) {
        BookingService bookingService = new BookingService();

        List<Ticket> freeTickets = bookingService.getAllFree();
        System.out.println(Reporter.getAllTicket(freeTickets, "ALL FREE"));

        Calendar date = Calendar.getInstance();
        date.set(2010, 10, 1);
        Ticket bookTicket = bookingService.bookTicket(2,
                new Person("SSS", "dd", "hjd", date.getTime()));
        freeTickets = bookingService.getAllFree();
        List<Ticket> ticketsInSystem = bookingService.getAllTicketInSystem();
        System.out.println(Reporter.bookedTicket(bookTicket, freeTickets, ticketsInSystem));

        bookTicket = bookingService.getByNumber(bookTicket.getNumberBook());
        ticketsInSystem = bookingService.getAllTicketInSystem();
        System.out.println(Reporter.getTicketByNumberBook(bookTicket, ticketsInSystem));

        System.out.println("True buy");
        try {
            bookingService.buyTicket(bookTicket);
        } catch (BookingException e) {
            e.printStackTrace();
        }
        ticketsInSystem = bookingService.getAllTicketInSystem();
        System.out.println(Reporter.buyTicket(ticketsInSystem));
        System.out.println("Bad buy");
        try {
            bookingService.buyTicket(ticketsInSystem.get(0));
        } catch (BookingException e) {
            e.printStackTrace();
        }
        ticketsInSystem = bookingService.getAllTicketInSystem();
        System.out.println(Reporter.buyTicket(ticketsInSystem));

        System.out.println("Return ticket");
        try {
            bookingService.returnTicket(ticketsInSystem.get(0).getNumberBook());
        } catch (BookingException e) {
            e.printStackTrace();
        }
        ticketsInSystem = bookingService.getAllTicketInSystem();
        freeTickets = bookingService.getAllFree();
        System.out.println(Reporter.returnTicket(ticketsInSystem, freeTickets));

    }
}
