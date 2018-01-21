package com.theaterseating.services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.theaterseating.bo.TheaterLayout;
import com.theaterseating.bo.TheaterSeatingRequest;

public interface TheaterSeatingService {
    
    TheaterLayout getTheaterLayout(Map<Integer, LinkedList<Integer>> layOut);
    
    List<TheaterSeatingRequest> getTicketRequests(Map<Integer, LinkedHashMap<String, Integer>> ticketRequest);
    
    void processTicketRequests(TheaterLayout layout, List<TheaterSeatingRequest> requests);

	void processAllTicketRequests(Map<Integer, LinkedList<Integer>> theaterMap,Map<Integer, LinkedHashMap<String, Integer>> requestName);

}
