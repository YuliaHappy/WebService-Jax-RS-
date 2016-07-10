package com.epam.training.webservice.server.dao;

import com.epam.training.webservice.common.domains.Person;
import com.epam.training.webservice.common.domains.Ticket;

import java.util.List;

public interface TicketDao {
    Ticket saveToSystem(int idTicket, Person person);
    void addTicket(Ticket ticket);
    List<Ticket> getAll();
}
