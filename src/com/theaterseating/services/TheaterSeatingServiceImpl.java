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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.theaterseating.bo.TheaterLayout;
import com.theaterseating.bo.TheaterSeatingRequest;
import com.theaterseating.bo.TheaterSeatingRequest.UserStatus;
import com.theaterseating.bo.TheaterSection;

public class TheaterSeatingServiceImpl implements TheaterSeatingService {

	/**
	 * Its take the raw input data and parses the string and converts in to
	 * TheaterLayOutObject
	 * 
	 * @return A TheaterLayout data type.
	 */

	@Override
	public TheaterLayout getTheaterLayout(Map<Integer, List<Integer>> theaterMap) throws NumberFormatException {

		int totalCapacity = 0;
		TheaterLayout theaterLayout = new TheaterLayout();
		List<TheaterSection> sectionsList = new ArrayList<TheaterSection>();

		for (Entry<Integer, List<Integer>> entry : theaterMap.entrySet()) {
			int counter = 1;

			LinkedList<Integer> sectionCapacity = (LinkedList<Integer>) entry.getValue();
			Iterator<Integer> itr = sectionCapacity.iterator();
			while (itr.hasNext()) {
				int capacity = itr.next();
				TheaterSection section = new TheaterSection();
				section.setRowNumber(entry.getKey());
				section.setSectionNumber(counter);
				section.setCapacity(capacity);
				section.setAvailableSeats(capacity);
				sectionsList.add(section);
				totalCapacity = totalCapacity + capacity;
				counter++;
			}

		}
		theaterLayout.setTotalCapacity(totalCapacity);
		theaterLayout.setAvailableSeats(totalCapacity);
		theaterLayout.setSections(sectionsList);
		return theaterLayout;

	}



	@Override
	public List<TheaterSeatingRequest> getTicketRequests(Map<Integer, Map<String, Integer>> requestName)
			throws NumberFormatException {

		List<TheaterSeatingRequest> requestsList = new LinkedList<TheaterSeatingRequest>();

		for (Entry<Integer, Map<String, Integer>> entry : requestName.entrySet()) {

			TheaterSeatingRequest request = new TheaterSeatingRequest();
			LinkedHashMap<String, Integer> requestMap = (LinkedHashMap<String, Integer>) entry.getValue();

			request.setName(requestMap.keySet().iterator().next());
			 request.setStatus(UserStatus.COMPLETE.toString());
			request.setNoOfTickets(requestMap.values().iterator().next());
			request.setRowNumber(entry.getKey());

			requestsList.add(request);

		}

		return requestsList;

	}

	/**
	 * Find complementing request to avoid waste of seats.
	 * 
	 * We start index from currentRequestIndex+1, because all previous requests are
	 * already completed.
	 * 
	 * @return A int data type.
	 * 
	 */

	private int findComplementRequest(List<TheaterSeatingRequest> requests, int complementSeats,
			int currentRequestIndex) {

		int requestNo = -1;

		for (int i = currentRequestIndex + 1; i < requests.size(); i++) {

			TheaterSeatingRequest request = requests.get(i);

		   if(!request.getStatus().equals(UserStatus.COMPLETE) && request.getNoOfTickets() == complementSeats){
         
				requestNo = i;
				break;

			}

		}

		return requestNo;
	}

	/**
	 * Find section by it's available seats
	 * 
	 */
	private int findSectionByAvailableSeats(List<TheaterSection> sections, int availableSeats) {

		int i = 0;
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

		/**
		 * sectionNo < 0 means could not find section sectionNo = 0 means found section
		 * and it's very first one sectionNo > 0 means found section but have to check
		 * for duplicate sections
		 * 
		 */

		if (sectionNo > 0) {

			for (i = sectionNo - 1; i >= 0; i--) {

				TheaterSection s = sections.get(i);

				if (s.getAvailableSeats() != availableSeats)
					break;

			}

			sectionNo = i + 1;

		}

		return sectionNo;
	}

	/*
	 * 
	 * Request Processing in nut-shell
	 * 
	 * 1) Iterate over all ticket requests 2) For each request,
	 * 
	 * - if total available seats are less than requested seats then 'we can't
	 * handle the party'. - iterate over all theater sections starting from first
	 * row
	 * 
	 * - If requested tickets and section's available seats match EXACTLY then
	 * assign it.
	 * 
	 * - If requested tickets < section's available seats - Find complement request,
	 * if any (complement request = section's available seats - original requested
	 * tickets) - If FOUND, complete assignment of original and complement ticket
	 * requests - If NOT found - Find EXCATLY matching section with requested no of
	 * tickets - If FOUND, assign it - If NOT found, then assign the request to
	 * current section
	 * 
	 * - If request is INCOMPLETE, 'Call party to split.'
	 * 
	 */

	@Override
	public void processTicketRequests(TheaterLayout layout, List<TheaterSeatingRequest> requests) {

		for (int i = 0; i < requests.size(); i++) {

			TheaterSeatingRequest request = requests.get(i);
			if(request.getStatus().equals(UserStatus.COMPLETE))   continue;

			/*
			 * -2 is an indicator when we can't handle the party.
			 */
			if (request.getNoOfTickets() > layout.getAvailableSeats()) {

				 request.setStatus(UserStatus.CANNOTHANDLE.toString());
				continue;

			}

			List<TheaterSection> sections = layout.getSections();

			for (int j = 0; j < sections.size(); j++) {

				TheaterSection section = sections.get(j);

				if (request.getNoOfTickets() == section.getAvailableSeats()) {

					request.setRowNumber(section.getRowNumber());
					request.setSectionNumber(section.getSectionNumber());
					section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
					layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
					request.setStatus(UserStatus.COMPLETE.toString());
					break;

				} else if (request.getNoOfTickets() < section.getAvailableSeats()) {

					int requestNo = findComplementRequest(requests,
							section.getAvailableSeats() - request.getNoOfTickets(), i);

					if (requestNo != -1) {

						request.setRowNumber(section.getRowNumber());
						request.setSectionNumber(section.getSectionNumber());
						section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
						layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
						 request.setStatus(UserStatus.COMPLETE.toString());

						TheaterSeatingRequest complementRequest = requests.get(requestNo);

						complementRequest.setRowNumber(section.getRowNumber());
						complementRequest.setSectionNumber(section.getSectionNumber());
						section.setAvailableSeats(section.getAvailableSeats() - complementRequest.getNoOfTickets());
						layout.setAvailableSeats(layout.getAvailableSeats() - complementRequest.getNoOfTickets());
						complementRequest.setStatus(UserStatus.COMPLETE.toString());

						break;

					} else {

						int sectionNo = findSectionByAvailableSeats(sections, request.getNoOfTickets());

						if (sectionNo >= 0) {

							TheaterSection perferctSection = sections.get(sectionNo);

							request.setRowNumber(perferctSection.getRowNumber());
							request.setSectionNumber(perferctSection.getSectionNumber());
							perferctSection
									.setAvailableSeats(perferctSection.getAvailableSeats() - request.getNoOfTickets());
							layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
							request.setStatus(UserStatus.COMPLETE.toString());
							break;

						} else {

							request.setRowNumber(section.getRowNumber());
							request.setSectionNumber(section.getSectionNumber());
							section.setAvailableSeats(section.getAvailableSeats() - request.getNoOfTickets());
							layout.setAvailableSeats(layout.getAvailableSeats() - request.getNoOfTickets());
							 request.setStatus(UserStatus.COMPLETE.toString());
							break;

						}

					}

				}

			}

			/*
			 * -1 is an indicator when we need to split the party.
			 */
			if (!request.getStatus().equals(UserStatus.COMPLETE.toString())) {

				request.setStatus(UserStatus.CALLTOSPLIT.toString());

			}

		}

		System.out.println("Seats Distribution.\n");

		for (TheaterSeatingRequest request : requests) {

			System.out.println(request.getStatus());

		}

	}

	@Override
	public void processAllTicketRequests(Map<Integer, List<Integer>> theaterMap,
			Map<Integer, Map<String, Integer>> requestName) {
		TheaterLayout theaterLayout = this.getTheaterLayout(theaterMap);

		List<TheaterSeatingRequest> seating = this.getTicketRequests(requestName);
		System.out.println("theaterLayout" + theaterLayout);
		System.out.println("seating" + seating);
		this.processTicketRequests(theaterLayout, seating);

	}

}
