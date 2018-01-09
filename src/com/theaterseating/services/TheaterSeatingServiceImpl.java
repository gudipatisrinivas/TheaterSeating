/**
* 
* This is a TheaterSeatingServiceImpl class
* 
*
* @author  Srinivas Gudipati
* @version 1.0
* @since   2017-08-01 
*/
package com.theaterseating.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.theaterseating.BO.TheaterLayout;
import com.theaterseating.BO.TheaterSeatingRequest;
import com.theaterseating.BO.TheaterSection;

public class TheaterSeatingServiceImpl implements TheaterSeatingService {

    @Override
    public TheaterLayout getTheaterLayout(String rawLayout) throws NumberFormatException{
        
        TheaterLayout theaterLayout = new TheaterLayout();
        TheaterSection section=new TheaterSection();
        List<TheaterSection> sectionsList = new ArrayList<TheaterSection>();
        int totalCapacity = 0, value;
        String[] rows = rawLayout.split(System.lineSeparator());
        String[] sections;
        for(int counterA=0 ; counterA<rows.length ; counterA++){
            
            sections = rows[counterA].split(" ");
            
            for(int counterB=0 ; counterB<sections.length ; counterB++){
            
                try{
                
                    value = Integer.valueOf(sections[counterB]);
                    
                }catch(NumberFormatException nfe){
                    
                    throw new NumberFormatException("'" + sections[counterB] + "'" + " is invalid section capacity. Please correct it.");
                    
                }
                
                totalCapacity = totalCapacity + value;
                
                section = new TheaterSection();
                section.setRowNumber(counterA + 1);
                section.setSectionNumber(counterB + 1);
                section.setCapacity(value);
                section.setAvailableSeats(value);
                
                sectionsList.add(section);
                
            }

        }
        
        theaterLayout.setTotalCapacity(totalCapacity);
        theaterLayout.setAvailableSeats(totalCapacity);
        theaterLayout.setSections(sectionsList);
        rows=null;
        sections=null;
        
        
        return theaterLayout;
        
    }

    @Override
    public List<TheaterSeatingRequest> getTicketRequests(String ticketRequests) throws NumberFormatException{
        
        List<TheaterSeatingRequest> requestsList = new ArrayList<TheaterSeatingRequest>();
        TheaterSeatingRequest request;
        
        String[] requests = ticketRequests.split(System.lineSeparator());
        
        for(String r : requests){
            
            String[] rData = r.split(" ");
            
            request = new TheaterSeatingRequest();
            
            request.setName(rData[0]);
            
            try{
            
                request.setNoOfTickets(Integer.valueOf(rData[1]));
                
            }catch(NumberFormatException nfe){
                
                throw new NumberFormatException("'" + rData[1] + "'" + " is invalid ticket request. Please correct it.");
            }
            request.setCompleted(false);
            
            requestsList.add(request);
            
        }
        
        return requestsList;
        
    }
    
    /*
     * Find complementing request to avoid waste of seats.
     * 
     * We start index from currentRequestIndex+1, because all previous requests are already completed.
     * 
     */
    private int findComplementRequest(List<TheaterSeatingRequest> requests, int complementSeats, int currentRequestIndex){
        
        int requestNo = -1;

        for(int i=currentRequestIndex+1 ; i<requests.size() ; i++){
            
            TheaterSeatingRequest request = requests.get(i);
            
            if(!request.isCompleted() && request.getNoOfTickets() == complementSeats){
                
                requestNo = i;
                break;
                
            }
            
        }
        
        return requestNo;
    }
    
    
    /*
     * Find section by it's available seats
     * 
     */
    private int findSectionByAvailableSeats(List<TheaterSection> sections, int availableSeats){
        
        int i=0;
        TheaterSection section = new TheaterSection();
        section.setAvailableSeats(availableSeats);
        
        Collections.sort(sections);
        
        Comparator<TheaterSection> byAvailableSeats = new Comparator<TheaterSection>() {
            
            @Override
            public int compare(TheaterSection o1, TheaterSection o2) {
                
                return o1.getAvailableSeats() - o2.getAvailableSeats();
                
            }
        };
        
        int sectionNo = Collections.binarySearch(sections, section, byAvailableSeats);
        
        /*
         * sectionNo < 0 means could not find section
         * sectionNo = 0 means found section and it's very first one
         * sectionNo > 0 means found section but have to check for duplicate sections
         * 
         */
        
        if(sectionNo > 0){
            
            for(i=sectionNo-1 ; i>=0 ; i--){
                
                TheaterSection s = sections.get(i);
                
                if(s.getAvailableSeats() != availableSeats) break;
                
            }
            
            sectionNo = i + 1;
            
        }
        
        return sectionNo;
    }
    
    /*
     *  
     * Request Processing in nut-shell
     * 
     * 1) Iterate over all ticket requests
     * 2) For each request, 
     * 
     *      - if total available seats are less than requested seats then 'we can't handle the party'.
     *      - iterate over all theater sections starting from first row
     *      
     *          - If requested tickets and section's available seats match EXACTLY then assign it.
     *          
     *          - If requested tickets < section's available seats
     *              - Find complement request, if any (complement request = section's available seats - original requested tickets)
     *                  - If FOUND, complete assignment of original and complement ticket requests
     *                  - If NOT found
     *                      - Find EXCATLY matching section with requested no of tickets
     *                          - If FOUND, assign it
     *                          - If NOT found, then assign the request to current section
     *                          
     *      - If request is INCOMPLETE, 'Call party to split.'
     * 
     */
    
    @Override
    public void processTicketRequests(TheaterLayout layout, List<TheaterSeatingRequest> requests) {
        
        for(int i=0 ; i<requests.size() ; i++){
            
            TheaterSeatingRequest request = requests.get(i);
            if(request.isCompleted())   continue;
            
            /*
             * -2 is an indicator when we can't handle the party.
             */
            if(request.getNoOfTickets() > layout.getAvailableSeats()){
                
                request.setRowNumber(-2);
                request.setSectionNumber(-2);
                continue;
                
            }
            
            List<TheaterSection> sections = layout.getSections();
            
            for(int j=0 ; j<sections.size() ; j++){
                
                TheaterSection section = sections.get(j);
                
                if(request.getNoOfTickets() == section.getAvailableSeats()){
                    
                    request.setRowNumber(section.getRowNumber());
                    request.setSectionNumber(section.getSectionNumber());
                    section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                    layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                    request.setCompleted(true);
                    break;
                    
                }else if(request.getNoOfTickets() < section.getAvailableSeats()){
                    
                    int requestNo = findComplementRequest(requests, section.getAvailableSeats() - request.getNoOfTickets(), i);
                    
                    if(requestNo != -1){
                        
                        request.setRowNumber(section.getRowNumber());
                        request.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                        request.setCompleted(true);
                        
                        TheaterSeatingRequest complementRequest = requests.get(requestNo);
                        
                        complementRequest.setRowNumber(section.getRowNumber());
                        complementRequest.setSectionNumber(section.getSectionNumber());
                        section.setAvailableSeats(section.getAvailableSeats() - complementRequest.getNoOfTickets());
                        layout.setAvailableSeats(layout.getAvailableSeats() - complementRequest.getNoOfTickets());
                        complementRequest.setCompleted(true);
                        
                        break;
                        
                    }else{
                        
                        int sectionNo = findSectionByAvailableSeats(sections, request.getNoOfTickets());
                        
                        if(sectionNo >= 0){
                            
                            TheaterSection perferctSection = sections.get(sectionNo);
                            
                            request.setRowNumber(perferctSection.getRowNumber());
                            request.setSectionNumber(perferctSection.getSectionNumber());
                            perferctSection.setAvailableSeats(perferctSection.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;
                            
                        }else{
                            
                            request.setRowNumber(section.getRowNumber());
                            request.setSectionNumber(section.getSectionNumber());
                            section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
                            layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
                            request.setCompleted(true);
                            break;
                            
                        }
                        
                    }
                    
                }
                
            }
            
            /*
             * -1 is an indicator when we need to split the party.
             */
            if(!request.isCompleted()){
                
                request.setRowNumber(-1);
                request.setSectionNumber(-1);
                
            }
            
        }
        
        System.out.println("Seats Distribution.\n");
        
        for(TheaterSeatingRequest request : requests){
            
            System.out.println(request.getStatus());
            
        }
        
    }

}
