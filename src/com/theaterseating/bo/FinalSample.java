package com.theaterseating.bo;

import java.util.HashMap;

public class FinalSample {
	
	
		   public String reverseWords(String s) {
				String[] s2 = s.split(" ");
				StringBuilder res = new StringBuilder();
				for (String word : s2) {
					
				res.append(new StringBuffer(word).reverse().toString() + " ");
		        }
		        return res.toString().trim();
				
			}
		

	public int lengthOfLongestSubstring(String s) {
		if (s.length() == 0)
			return 0;
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		int max = 0;
		for (int i = 0, j = 0; i < s.length(); ++i) {
			if (map.containsKey(s.charAt(i))) {
				j = Math.max(j, map.get(s.charAt(i)) + 1);
			}
			map.put(s.charAt(i), i);
			max = Math.max(max, i - j + 1);
		}
		System.out.println(map);
		return max;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new FinalSample().reverseWords("Let's take LeetCode contest");

	}

}
