package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


/*
 * Class Name: JobListing
 * Description: This class represents a job listing entity
 *              with properties like title, description, and location.
 * Author: Chandru
 * Date: 13-Aug-2025
 */


@Entity
@Table(name = "job_listings")
public class JobListing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobListingId;

    private String title;
    private String description;
    private String qualifications;
    private String location;
    private double salary; 
    private LocalDate postedDate;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;

    @OneToMany(mappedBy = "jobListing", cascade = CascadeType.ALL)
    private List<Application> applications;

    public JobListing() {}
    

    public JobListing(int jobListingId, String title, String description, String qualifications, String location,
			double salary, LocalDate postedDate, Employee employee, List<Application> applications) {
    	
		this.jobListingId = jobListingId;
		this.title = title;
		this.description = description;
		this.qualifications = qualifications;
		this.location = location;
		this.salary = salary;
		this.postedDate = postedDate;
		this.employee = employee;
		this.applications = applications;
	}

   
    public int getJobListingId() {
        return jobListingId;
    }

    public void setJobListingId(int jobListingId) {
        this.jobListingId = jobListingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    

    public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public LocalDate getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDate postedDate) {
		this.postedDate = postedDate;
	}

	public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
