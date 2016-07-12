package com.epam.training.webservice.common.exceptions;

import javax.xml.ws.WebFault;

@WebFault
public class BookingException extends Exception {
    public BookingException(String message) {
        super("Error in booking service " + message);
    }
}
