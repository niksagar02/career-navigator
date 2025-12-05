package com.example.api.in.web.service;

import com.example.api.in.web.model.JobRecommendation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for communicating with the Adzuna Jobs API.
 *
 * This class:
 *  - Builds the API request URL using user inputs
 *  - Handles pagination via dynamic "page" parameter
 *  - Parses JSON responses returned from Adzuna
 *  - Creates and returns a list of JobRecommendation objects
 *
 * It contains all the business logic related to fetching and processing
 * job search results, keeping the controller clean.
 */
@Service
public class JobRecommendationService {

    // API credentials (normally stored in properties, but kept inline for this project)
    private static final String APP_ID  = "31adfaa8";
    private static final String APP_KEY = "a5db5e21b4c95183e249568d9d381dd4";

    /**
     * Fetches job recommendations from the Adzuna API.
     *
     * @param skill    required search keyword
     * @param location optional location filter; defaults to "United States"
     * @param page     dynamic page number used for pagination
     * @return a list of JobRecommendation objects
     */
    public List<JobRecommendation> getRecommendations(String skill, String location, int page) {

        List<JobRecommendation> jobs = new ArrayList<>();

        try {
            // If skill is empty or null → return empty list immediately
            if (skill == null || skill.isBlank()) {
                return jobs;
            }

            // Encode parameters for safe API usage
            String safeSkill = URLEncoder.encode(skill.trim(), StandardCharsets.UTF_8);
            String safeLocation = URLEncoder.encode(
                    (location == null || location.isBlank()) ? "United States" : location.trim(),
                    StandardCharsets.UTF_8
            );

            // Build the dynamic API URL (pagination supported)
            String apiUrl = "https://api.adzuna.com/v1/api/jobs/us/search/" + page
                    + "?app_id=" + APP_ID
                    + "&app_key=" + APP_KEY
                    + "&results_per_page=4"
                    + "&what=" + safeSkill
                    + "&where=" + safeLocation;

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

            System.out.println("ADZUNA PAGE " + page + " RAW RESPONSE: " + response.getBody());

            // Parse JSON
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode results = root.path("results");

            // If no results, return empty list
            if (!results.isArray() || results.size() == 0) {
                return jobs;
            }

            // Loop through each job in the JSON response
            for (JsonNode job : results) {

                String title = job.path("title").asText("Not found");
                String company = job.path("company").path("display_name").asText("N/A");
                String loc = job.path("location").path("display_name").asText(location);

                // Extract salary range (if provided)
                JsonNode minNode = job.path("salary_min");
                JsonNode maxNode = job.path("salary_max");

                String salary = "Not provided";
                if (!minNode.isMissingNode() || !maxNode.isMissingNode()) {
                    String minVal = minNode.asText("");
                    String maxVal = maxNode.asText("");
                    salary = minVal + (maxVal.isEmpty() ? "" : " - " + maxVal);
                }

                String description = job.path("description").asText("No description available");

                // Apply URL for job redirection
                String url = job.path("redirect_url").asText("");

                // Create job recommendation object
                JobRecommendation rec =
                        new JobRecommendation(title, company, loc, salary, description);

                // Add apply link
                rec.setApplyUrl(url);

                jobs.add(rec);
            }

        } catch (Exception e) {
            System.out.println("Adzuna API error: " + e.getMessage());
            e.printStackTrace();
        }

        return jobs;
    }
}
