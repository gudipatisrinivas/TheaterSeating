package com.theaterseating.services.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.theaterseating.services.TheaterSeatingService;
import com.theaterseating.services.TheaterSeatingServiceImpl;

public class TheaterSeatingServiceTest {
	
	TheaterSeatingService theaterSeatingService;
	
	@BeforeClass
	public void intiate() {
		theaterSeatingService=new TheaterSeatingServiceImpl();
	}
	
	@Test
	public void processAllTicketRequestsTest() {
	
		Map<Integer, List<Integer>> theaterSeatingMap = new LinkedHashMap<>();

		List<Integer> theaterSection1 = new LinkedList();
		theaterSection1.addAll(Arrays.asList(6, 6));
		theaterSeatingMap.put(1, theaterSection1);

		List<Integer> theaterSection2 = new LinkedList();
		theaterSection2.addAll(Arrays.asList(3, 5, 5, 3));
		theaterSeatingMap.put(2, theaterSection2);

		List<Integer> theaterSection3 = new LinkedList();
		theaterSection3.addAll(Arrays.asList(3, 5, 5, 3));
		theaterSeatingMap.put(3, theaterSection3);

		List<Integer> theaterSection4 = new LinkedList();
		theaterSection4.addAll(Arrays.asList(4, 6, 6, 4));
		theaterSeatingMap.put(4, theaterSection4);

		List<Integer> theaterSection5 = new LinkedList();
		theaterSection4.addAll(Arrays.asList(2, 8, 8, 2));
		theaterSeatingMap.put(5, theaterSection4);

		Map<Integer, Map<String, Integer>> requestDetails = new LinkedHashMap<>();
		LinkedHashMap<String, Integer> request1 = new LinkedHashMap<String, Integer>();
		request1.put("Smith", 2);

		LinkedHashMap<String, Integer> request2 = new LinkedHashMap<String, Integer>();
		request2.put("Jones", 5);

		LinkedHashMap<String, Integer> request3 = new LinkedHashMap<String, Integer>();
		request3.put("Davis", 6);

		LinkedHashMap<String, Integer> request4 = new LinkedHashMap<String, Integer>();
		request4.put("Wilson", 100);

		LinkedHashMap<String, Integer> request5 = new LinkedHashMap<String, Integer>();
		request5.put("Johnson", 3);

		LinkedHashMap<String, Integer> request6 = new LinkedHashMap<String, Integer>();
		request6.put("Williams", 4);
		requestDetails.put(1, request1);

		requestDetails.put(2, request2);

		requestDetails.put(3, request3);
		requestDetails.put(4, request4);

		requestDetails.put(5, request5);

		requestDetails.put(6, request6);

		theaterSeatingService.processAllTicketRequests(theaterSeatingMap, requestDetails);
		
		
		
		
	}
		

}
