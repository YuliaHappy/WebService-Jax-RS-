package com.epam.training.webservice.common.domains;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;

@XmlRootElement(name = "ticket")
public class Ticket {
    private int id;
    private int numberBook;
    private String departureCity;
    private String arrivalTown;
    private Calendar departureDate;
    private Calendar arrivalDate;
    private int price;
    private StateTicket state;
    private Person person;

    public Ticket() {
    }

    public Ticket(int id, String departureCity, String arrivalTown,
                  Calendar departureDate, Calendar arrivalDate, int price) {
        this.id = id;
        this.departureCity = departureCity;
        this.arrivalTown = arrivalTown;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.state = StateTicket.FREE;
    }

    @XmlElement
    public int getNumberBook() {
        return numberBook;
    }

    public void setNumberBook(int numberBook) {
        this.numberBook = numberBook;
    }

    @XmlElement
    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    @XmlElement
    public String getArrivalTown() {
        return arrivalTown;
    }

    public void setArrivalTown(String arrivalTown) {
        this.arrivalTown = arrivalTown;
    }

    @XmlElement
    public Calendar getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Calendar departureDate) {
        this.departureDate = departureDate;
    }

    @XmlElement
    public Calendar getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Calendar arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @XmlElement
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @XmlElement
    public StateTicket getState() {
        return state;
    }

    public void setState(StateTicket state) {
        this.state = state;
    }

    @XmlElement
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", numberBook=" + numberBook +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalTown='" + arrivalTown + '\'' +
                ", departureDate=" + departureDate.get(1) + "/" + departureDate.get(2) + "/" + departureDate.get(5) + "/" + departureDate.get(10) + "/" + departureDate.get(12) +
                ", arrivalDate=" + arrivalDate.get(1) + "/" + arrivalDate.get(2) + "/" + arrivalDate.get(5) + "/" + arrivalDate.get(10) + "/" + arrivalDate.get(12) +
                ", price=" + price +
                ", state=" + state +
                ", person=" + person +
                '}';
    }
}
