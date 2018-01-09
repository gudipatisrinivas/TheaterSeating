/**
* 
* This is a TheaterLayout class which holds the objects for the Theater.
*
* @author  Srinivas Gudipati
* @version 1.0
* @since   2017-08-01 
*/

package com.theaterseating.BO;

import java.util.List;

public class TheaterLayout {

    private int totalCapacity;
    private int availableSeats;
    private List<TheaterSection> sections;

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(int totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<TheaterSection> getSections() {
        return sections;
    }

    public void setSections(List<TheaterSection> sections) {
        this.sections = sections;
    }
    
}
