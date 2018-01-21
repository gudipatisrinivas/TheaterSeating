/**
* 
* This is a TheaterSeatingMain class
* 
*
* @author  Srinivas Gudipati
* @version 1.0
* @since   2017-08-01 
*/
package com.theaterseating.controller;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

import com.theaterseating.services.TheaterSeatingService;
import com.theaterseating.services.TheaterSeatingServiceImpl;

public class TheaterSeatingMain {

	public static void main(String[] args) {

		boolean isDone = false;
		Map<Integer, LinkedList<Integer>> theaterMap = new LinkedHashMap<Integer, LinkedList<Integer>>();
		Map<Integer, LinkedHashMap<String, Integer>> requestName = new LinkedHashMap<Integer, LinkedHashMap<String, Integer>>();
		while (!isDone) {

			System.out.println("Welcome to the Theater Layout");
			System.out.println("Please enter the no of rows in the Layout");

			Scanner input = new Scanner(System.in);
			Integer noOfRows = Integer.parseInt(input.nextLine());
			for (int counterA = 1; counterA <= noOfRows; counterA++) {
				System.out.println("Please enter the no of sections for the row\t" + counterA);
				Integer noOfSection = Integer.parseInt(input.nextLine());
				LinkedList<Integer> section = new LinkedList<Integer>();
				for (int counterB = 1; counterB <= noOfSection; counterB++) {
					System.out.println("Please enter the capacity for counter" + counterA + "the section\t" + counterB);
					Integer sectionCapacity = Integer.parseInt(input.nextLine());
					section.add(sectionCapacity);

				}
				theaterMap.put(counterA, section);

			}
			System.out.println("Please enter the no of Requests");

			Integer noOfRequests = Integer.parseInt(input.nextLine());
			for (int i = 0; i < noOfRequests; i++) {

				System.out.println("Please enter the name of the request for the request" + i);
				String nameOfTheRequest = input.nextLine();
				System.out.println("Please enter the order of the request for the request");
				Integer order = Integer.parseInt(input.nextLine());
				LinkedHashMap<String, Integer> value = new LinkedHashMap<String, Integer>();
				value.put(nameOfTheRequest, order);
				requestName.put(i, value);
			}
			System.out.println("Done");
			isDone = true;
			input.close();
		}

		System.out.println(theaterMap.toString());
		TheaterSeatingService service = new TheaterSeatingServiceImpl();

		try {

			service.processAllTicketRequests(theaterMap, requestName);

		} catch (NumberFormatException nfe) {

			System.out.println(nfe.getMessage());

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
