package com.epam.training.webservice.server.dao.impl;

import com.epam.training.webservice.common.domains.StateTicket;
import com.epam.training.webservice.common.domains.Ticket;
import com.epam.training.webservice.server.dao.TicketDao;
import com.epam.training.webservice.common.domains.Person;

import java.util.*;

public class MemoryTicketDaoImpl implements TicketDao {
    private Map<Integer, Ticket> tickets;

    public MemoryTicketDaoImpl() {
        tickets = new HashMap<>();
        tickets.put(1,
                new Ticket(1, "Tver", "Moscow",
                        new GregorianCalendar(2016, 7, 7, 6, 0),
                        new GregorianCalendar(2016, 7, 8, 7, 0), 1000));
        tickets.put(2,
                new Ticket(2, "Moscow", "Tver",
                        new GregorianCalendar(2016, 7, 8, 6, 0),
                        new GregorianCalendar(2016, 7, 9, 7, 0), 2000));
        tickets.put(3,
                new Ticket(3, "Tver", "St.Peterburg",
                        new GregorianCalendar(2016, 8, 7, 6, 0),
                        new GregorianCalendar(2016, 8, 8, 7, 0), 1500));
        tickets.put(4,
                new Ticket(4, "St.Peterburg", "Tver",
                        new GregorianCalendar(2016, 8, 8, 6, 0),
                        new GregorianCalendar(2016, 8, 9, 7, 0), 3000));
        tickets.put(5,
                new Ticket(5, "Tver", "Moscow",
                        new GregorianCalendar(2015, 7, 7, 6, 0),
                        new GregorianCalendar(2015, 7, 8, 7, 0), 2500));
    }


    @Override
    public Ticket saveToSystem(int idTicket, Person person) {
        Ticket ticket = tickets.remove(idTicket);
        ticket.setNumberBook(new Random().nextInt());
        ticket.setState(StateTicket.BOOKED);
        ticket.setPerson(person);
        return ticket;
    }

    @Override
    public void addTicket(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
        ticket.setState(StateTicket.FREE);
        ticket.setPerson(null);
    }

    @Override
    public List<Ticket> getAll() {
        return new ArrayList<>(tickets.values());
    }
}
