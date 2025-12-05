package com.example.api.in.web.controller;

import com.example.api.in.web.model.JobRecommendation;
import com.example.api.in.web.service.JobRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class responsible for handling job search requests.
 * 
 * This class manages:
 *  - The initial job search submission (page 1)
 *  - Subsequent AJAX "load more" requests for additional pages
 *  - Passing job results to the frontend (Thymeleaf template)
 *  - Storing accumulated jobs in the session
 * 
 * It delegates all job-fetching logic to JobRecommendationService.
 */
@Controller
public class JobController {

    @Autowired
    private JobRecommendationService jobService;

    /**
     * Handles AJAX requests for loading additional job pages.
     * Returns results as JSON without refreshing the page.
     *
     * @param skill           required search keyword
     * @param location        optional job location
     * @param experienceYears optional experience filter (currently informational)
     * @param page            the page number to fetch
     * @return a list of job recommendations in JSON format
     */
    @GetMapping("/load-more")
    @ResponseBody
    public List<JobRecommendation> loadMore(
            @RequestParam String skill,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experienceYears,
            @RequestParam int page
    ) {
        return jobService.getRecommendations(skill, location, page);
    }

    /**
     * Handles the first job search request (page = 1).
     * Fetches initial results, stores them in the session, 
     * and passes them to index.html for display.
     *
     * @param skill           required search keyword
     * @param location        optional location filter
     * @param experienceYears optional input for future use
     * @param model           used to pass data to the HTML view
     * @param session         stores accumulated job results across requests
     * @return the "index" Thymeleaf template
     */
    @PostMapping("/recommend")
    public String recommendJob(
            @RequestParam String skill,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String experienceYears,
            Model model,
            HttpSession session
    ) {

        // Initialize/reset the job list for a new search
        List<JobRecommendation> allJobs = new ArrayList<>();
        session.setAttribute("allJobs", allJobs);

        // Fetch first page of job results
        List<JobRecommendation> page1Jobs = jobService.getRecommendations(skill, location, 1);

        // Add the results to the session list
        allJobs.addAll(page1Jobs);
        session.setAttribute("allJobs", allJobs);

        // Send data to the view
        model.addAttribute("jobs", allJobs);
        model.addAttribute("submitted", true);
        model.addAttribute("skill", skill);
        model.addAttribute("location", location);
        model.addAttribute("experienceYears", experienceYears);

        // If no jobs are found, inform the UI
        model.addAttribute("noJobs", page1Jobs.isEmpty());

        return "index";
    }
}
