package com.example.api.in.web.model;

/**
 * A simple data model representing a job recommendation.
 * 
 * This class stores:
 *  - Job title
 *  - Company name
 *  - Location
 *  - Salary information
 *  - Description snippet
 *  - Apply URL (redirect link from Adzuna)
 *
 * It acts as a POJO used by the service layer and controller to
 * pass job information to the view.
 */
public class JobRecommendation {

    private String jobTitle;
    private String companyName;
    private String location;
    private String salary;
    private String description;

    // Apply URL for external job application links
    private String applyUrl;

    /** Default constructor. */
    public JobRecommendation() {
    }

    /**
     * Constructor with the original 5 fields
     * (before applyUrl was added).
     */
    public JobRecommendation(String jobTitle, String companyName, String location, String salary, String description) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.salary = salary;
        this.description = description;
    }

    /**
     * Constructor supporting all fields, including applyUrl.
     */
    public JobRecommendation(String jobTitle, String companyName, String location, String salary,
                             String description, String applyUrl) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.location = location;
        this.salary = salary;
        this.description = description;
        this.applyUrl = applyUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getLocation() {
        return location;
    }

    public String getSalary() {
        return salary;
    }

    public String getDescription() {
        return description;
    }

    /**
     * @return the external Apply URL from Adzuna.
     */
    public String getApplyUrl() {
        return applyUrl;
    }

    /**
     * Sets the external Apply URL.
     */
    public void setApplyUrl(String applyUrl) {
        this.applyUrl = applyUrl;
    }
}
