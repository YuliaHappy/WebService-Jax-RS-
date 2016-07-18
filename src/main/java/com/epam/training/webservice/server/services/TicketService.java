package com.epam.training.webservice.server.services;

import com.epam.training.webservice.common.domains.StateTicket;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.common.exceptions.BookingException;
import com.epam.training.webservice.server.dao.TicketDao;
import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.server.dao.impl.MemoryTicketDaoImpl;

import java.util.*;

public class TicketService {
    private TicketDao ticketDao = new MemoryTicketDaoImpl();
    private Map<Integer, Ticket> processedTickets = new HashMap<>();

    private static final TicketService instance = new TicketService();
    private TicketService() {
    }

    public static TicketService getInstance() {
        return instance;
    }

    public Ticket saveToSystem(int idTicket, Person person) {
        return ticketDao.saveToSystem(idTicket, person);
    }

    public List<Ticket> getAll() {
        return Collections.unmodifiableList(ticketDao.getAll());
    }

    public void putTicket(Ticket ticket) {
        processedTickets.put(ticket.getNumberBook(), ticket);
    }

    public Ticket getByNumber(int numberTicket) {
        return processedTickets.get(numberTicket);
    }

    public List<Ticket> getAllInSystem() {
        return new ArrayList<>(processedTickets.values());
    }

    public Ticket buyTicket(int numberTicket) throws BookingException {
        Ticket ticketInSystem = processedTickets.get(numberTicket);
        if (ticketInSystem != null) {
            if (ticketInSystem.getState() == StateTicket.BOOKED) {
                ticketInSystem.setState(StateTicket.PAID);
                return ticketInSystem;
            }
            throw new BookingException("State ticket " + ticketInSystem.getState() + " incorret!");
        }
        throw new BookingException("Number ticket " + numberTicket + " incorret!");
    }

    public Ticket returnTicket(int numberTicket) throws BookingException {
        if (processedTickets.containsKey(numberTicket)) {
            Ticket ticket = removeTicketByNumber(numberTicket);
            addTicket(ticket);
            return ticket;
        }
        throw new BookingException("Number ticket " + numberTicket + " incorret!");
    }

    private void addTicket(Ticket ticket) {
        ticketDao.addTicket(ticket);
    }

    private Ticket removeTicketByNumber(int numberTicket) {
        return processedTickets.remove(numberTicket);
    }
}
