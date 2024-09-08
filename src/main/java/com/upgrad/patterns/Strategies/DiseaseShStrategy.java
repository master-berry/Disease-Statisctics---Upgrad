package com.upgrad.patterns.Strategies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upgrad.patterns.Config.RestServiceGenerator;
import com.upgrad.patterns.Entity.DiseaseShResponse;
import com.upgrad.patterns.Interfaces.IndianDiseaseStat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class DiseaseShStrategy implements IndianDiseaseStat {

    private Logger logger = LoggerFactory.getLogger(DiseaseShStrategy.class);

    private RestTemplate restTemplate;

    @Value("${config.diseaseSh-io-url}")
    private String baseUrl;

    /**
     * Constructor for the DiseaseShStrategy class.
     * Initializes the RestTemplate instance.
     */
    public DiseaseShStrategy() {
        restTemplate = RestServiceGenerator.GetInstance();
    }

    /**
     * Retrieves the active count of COVID-19 cases for India from the DiseaseShResponse.
     *
     * @return the confirmed cases count for India as a String rounded to 0 decimal places.
     * If the country is not India or if an error occurs, returns "0" or null, respectively.
     */
    @Override
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

    /**
     * Reads and parses the DiseaseSh.json file to obtain the DiseaseShResponse object.
     *
     * @return the DiseaseShResponse object containing data from the JSON file.
     * @throws IOException if there is an error reading the JSON file.
     */
    private DiseaseShResponse getDiseaseShResponseResponses() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("DiseaseSh.json");
        return objectMapper.readValue(resource.getFile(), DiseaseShResponse.class);
    }
}
