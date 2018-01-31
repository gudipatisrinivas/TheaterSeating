/**
* 
* This is a TheaterSeatingRequest class which holds the objects for the TheaterSeatingRequest.
*
* @author  Srinivas Gudipati
* @version 1.0
* @since   2017-08-01 
*/
package com.theaterseating.bo;
import java.util.*;

public class TheaterSeatingRequest {

    private String name;
    private int noOfTickets;
    private int rowNumber;
    private int sectionNumber;
    private String status;
    private List list;
    
    

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return status;
	}
	
	

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public void setNoOfTickets(int noOfTickets) {
        this.noOfTickets = noOfTickets;
    }


    
    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }
    
  
    
    public enum UserStatus {
    		COMPLETE("COMPLETE"),
        INCOMPLETE("INCOMPLETE"),
        CANNOTHANDLE("CANNOTHANDLE"),
        CALLTOSPLIT("CALLTOSPLIT");
    		
    	private final String text;
    	
		private UserStatus(String text) {
			this.text = text;
		}

       @Override
        public String toString() {
            return text;
        }
    }
    
	public String getUserStatus() {

		//String status = null;

		if (status.equals(UserStatus.COMPLETE.toString())) {

			status = name + " " + "Row " + rowNumber + " " + "Section " + sectionNumber;

		} else if (status.equals(UserStatus.CALLTOSPLIT.toString())) {

			status = name + " " + "Call to split party.";

		} else if (status.equals(UserStatus.CANNOTHANDLE.toString())) {

			status = name + " " + "Sorry, we can't handle your party.";

		}

		return status;
	}

	@Override
	public String toString() {
		return "TheaterSeatingRequest [name=" + name + ", noOfTickets=" + noOfTickets + ", rowNumber=" + rowNumber
				+ ", sectionNumber=" + sectionNumber + "]";
	}

}
