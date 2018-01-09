package com.theaterseating.services;

import java.util.List;

import com.theaterseating.BO.TheaterLayout;
import com.theaterseating.BO.TheaterSeatingRequest;

public interface TheaterSeatingService {
    
    TheaterLayout getTheaterLayout(String rawLayout);
    
    List<TheaterSeatingRequest> getTicketRequests(String ticketRequests);
    
    void processTicketRequests(TheaterLayout layout, List<TheaterSeatingRequest> requests);

}
