/**
* 
* This is a TheatreSeatingMain class
* 
*
* @author  Srinivas Gudipati
* @version 1.0
* @since   2017-08-01 
*/
package com.theaterseating.controller;

import java.util.List;
import java.util.Scanner;

import com.theaterseating.BO.TheaterLayout;
import com.theaterseating.BO.TheaterSeatingRequest;
import com.theaterseating.services.TheaterSeatingService;
import com.theaterseating.services.TheaterSeatingServiceImpl;

public class TheatreSeatingMain {

	public static void main(String[] args) {

		String line = "";
		StringBuilder layout = new StringBuilder();
		StringBuilder ticketRequests = new StringBuilder();
		boolean isLayoutFinished = false;

		System.out.println("Please enter Theater Layout and Ticket requests and then enter 'done'.\n");

		Scanner input = new Scanner(System.in);

		while ((line = input.nextLine()) != null && !line.equals("done")) {

			if (line.length() == 0) {

				isLayoutFinished = true;
				continue;

			}

			if (!isLayoutFinished) {

				layout.append(line + System.lineSeparator());

			} else {

				ticketRequests.append(line + System.lineSeparator());

			}

		}

		input.close();

		TheaterSeatingService service = new TheaterSeatingServiceImpl();

		try {

			TheaterLayout theaterLayout = service.getTheaterLayout(layout.toString());

			List<TheaterSeatingRequest> requests = service.getTicketRequests(ticketRequests.toString());

			service.processTicketRequests(theaterLayout, requests);

		} catch (NumberFormatException nfe) {

			System.out.println(nfe.getMessage());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}