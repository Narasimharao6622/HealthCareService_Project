package com.healthCareService.healthCareServiceProject.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpecialazationForKeyValuePair {
	
	public static Map<String, String> specializationPair() {
		Map<String, String> specialization = new HashMap<String, String>();
		specialization.put("Cardiologist", "Cardiology");
		specialization.put("Cardiology", "Cardiology");
		specialization.put("Cardio", "Cardiology");
		
		return specialization;
	}
}
