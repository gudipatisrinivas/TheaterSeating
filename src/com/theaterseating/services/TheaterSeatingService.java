package com.theaterseating.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.theaterseating.bo.TheaterLayout;
import com.theaterseating.bo.TheaterSeatingRequest;

public interface TheaterSeatingService {
    
    TheaterLayout getTheaterLayout(Map<Integer, List<Integer>> layOut);
    
    List<TheaterSeatingRequest> getTicketRequests(Map<Integer, Map<String, Integer>> ticketRequest);
    
    void processTicketRequests(TheaterLayout layout, List<TheaterSeatingRequest> requests);

    List<TheaterSeatingRequest> processAllTicketRequests(Map<Integer, List<Integer>> theaterMap,Map<Integer, Map<String, Integer>> requestName);

}
