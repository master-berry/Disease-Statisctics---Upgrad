package com.upgrad.patterns.Strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.patterns.Config.RestServiceGenerator;
import com.upgrad.patterns.Entity.DiseaseShResponse;
import com.upgrad.patterns.Interfaces.IndianDiseaseStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;

@Service
public class DiseaseShStrategy implements IndianDiseaseStat {

    private Logger logger = LoggerFactory.getLogger(DiseaseShStrategy.class);

    private RestTemplate restTemplate;

    @Value("${config.diseaseSh-io-url}")
    private String baseUrl;

    public DiseaseShStrategy()
    {
        restTemplate = RestServiceGenerator.GetInstance();
    }

    @Override
//    public String GetActiveCount() {
//    	//write a try catch block here
//
//    	//try block
//    	try {
//            DiseaseShResponse diseaseShResponse = getDiseaseShResponseResponses();
//            return String.valueOf(Math.round(diseaseShResponse.getCases()));
//
//        } catch (Exception e) {
//            logger.error("Error occured", e);
//            return null;
//        }
//        //obtain response from the getDiseaseShResponseResponses() method
//	    	//store it in an object
//
//    		//get the response using the getCases() method
//	    	//return the response after rounding it up to 0 decimal places
//
//
//    	//catch block
//    		//log the error
//
//    		//return null
//
//
//    }

    public String GetActiveCount() {
        try {
            // Obtain response from the getDiseaseShResponseResponses() method
            DiseaseShResponse response = getDiseaseShResponseResponses();

            // Check if the country is "India"
            if ("India".equalsIgnoreCase(response.getCountry())) {
                // Get the confirmed cases
                Float confirmedCases = response.getCases();

                // Return the confirmed cases after rounding to 0 decimal places
                return String.format("%.0f", confirmedCases);
            } else {
                return "0"; // If the country is not India, return 0
            }
        } catch (IOException e) {
            // Log the error
            logger.error("Error fetching the active count from DiseaseShResponse", e);

            // Return null or an appropriate error message
            return null;
        }
    }



    private DiseaseShResponse getDiseaseShResponseResponses() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("DiseaseSh.json");
        return objectMapper.readValue(resource.getFile(), DiseaseShResponse.class);

    }
}
