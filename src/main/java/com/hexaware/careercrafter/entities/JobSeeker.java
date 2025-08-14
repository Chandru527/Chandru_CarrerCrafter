package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;
import java.util.List;

/*
 * Class Name: JobSeeker
 * Description: This class represents a job seeker entity
 *              with personal information, resumes, and applied job details.
 * Author: Chandru
 * Date: 13-Aug-2025
 */


@Entity
@Table(name = "job_seekers")
public class JobSeeker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jobSeekerId;
    private String fullName;
    private String education;
    private String experience;
    private String skills;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

    @OneToOne(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private Resume resume;

    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<Application> applications;
    
    @OneToMany(mappedBy = "jobSeeker", cascade = CascadeType.ALL)
    private List<JobSearch> jobSearches;


   
    public JobSeeker() {}
    

    public JobSeeker(int jobSeekerId, String fullName, String education, String experience, String skills, User user,
			Resume resume, List<Application> applications) {
		this.jobSeekerId = jobSeekerId;
		this.fullName = fullName;
		this.education = education;
		this.experience = experience;
		this.skills = skills;
		this.user = user;
		this.resume = resume;
		this.applications = applications;
	}


    public int getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(int jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }
    

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
}
