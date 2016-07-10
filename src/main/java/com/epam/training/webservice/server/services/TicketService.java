package com.epam.training.webservice.server.services;

import com.epam.training.webservice.common.domains.StateTicket;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.server.dao.TicketDao;
import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.server.dao.impl.MemoryTicketDaoImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addTicket(Ticket ticket) {
        ticketDao.addTicket(ticket);
    }

    public List<Ticket> getAll() {
        return ticketDao.getAll();
    }

    public void putTicket(Ticket ticket) {
        processedTickets.put(ticket.getNumberBook(), ticket);
    }

    public Ticket getByNumber(int numberTicket) {
        return processedTickets.get(numberTicket);
    }

    public boolean containsTicketNumber(int ticketNumber) {
        return processedTickets.containsKey(ticketNumber);
    }

    public Ticket removeTicketByNumber(int ticketNumber) {
        return processedTickets.remove(ticketNumber);
    }

    public List<Ticket> getAllInSystem() {
        return new ArrayList<>(processedTickets.values());
    }

    public boolean buyTicket(int numberTicket) {
        Ticket ticketInSystem = processedTickets.get(numberTicket);
        if (ticketInSystem != null && ticketInSystem.getState() == StateTicket.BOOKED) {
            ticketInSystem.setState(StateTicket.PAID);
            return true;
        }
        return false;
    }

    public boolean returnTicket(int numberTicket) {
        if (processedTickets.containsKey(numberTicket)) {
            addTicket(removeTicketByNumber(numberTicket));
            return true;
        }
        return false;
    }
}
