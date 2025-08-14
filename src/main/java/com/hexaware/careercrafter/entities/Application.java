package com.hexaware.careercrafter.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

/*
 * Class Name: Application
 * Description: This class represents a job application entity
 *              containing details about the applicant and the job applied for.
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int applicationId;

    private String status;
    private LocalDate applicationDate;
    @Column(length = 500)
    private String filePath;

    
	@ManyToOne
    @JoinColumn(name = "job_listing_id", referencedColumnName = "jobListingId")
    private JobListing jobListing;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "jobSeekerId")
    private JobSeeker jobSeeker;

  
    public Application() {}
   
   
    public Application(int applicationId, String status, LocalDate applicationDate, String filePath,
			JobListing jobListing, JobSeeker jobSeeker) {
		this.applicationId = applicationId;
		this.status = status;
		this.applicationDate = applicationDate;
		this.filePath = filePath;
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
    public LocalDate getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(LocalDate applicationDate) {
		this.applicationDate = applicationDate;
	}


    public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
