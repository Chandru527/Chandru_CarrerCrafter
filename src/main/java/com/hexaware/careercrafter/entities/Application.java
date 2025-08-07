package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    private String status;

    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "jobId")
    private JobListing jobListing;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "jobSeekerId")
    private JobSeeker jobSeeker;

  
    public Application() {}

    public Application(int applicationId, String status, JobListing jobListing, JobSeeker jobSeeker) {
        this.applicationId = applicationId;
        this.status = status;
        this.jobListing = jobListing;
        this.jobSeeker = jobSeeker;
    }

   
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public JobListing getJobListing() {
        return jobListing;
    }

    public void setJobListing(JobListing jobListing) {
        this.jobListing = jobListing;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }
}
