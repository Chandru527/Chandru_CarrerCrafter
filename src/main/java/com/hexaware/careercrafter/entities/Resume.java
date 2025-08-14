package com.hexaware.careercrafter.entities;

import java.time.LocalDate;

import jakarta.persistence.*;

/*
 * Class Name: Resume
 * Description: This class represents a resume entity
 *              containing the job seeker’s education, experience, and skills details.
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resumeId;

    private String filePath;
    private LocalDate uploadDate;

    @OneToOne
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "jobSeekerId")
    private JobSeeker jobSeeker;

    
    public Resume() {}
    

    public Resume(int resumeId, String filePath, LocalDate uploadDate, JobSeeker jobSeeker) {
		super();
		this.resumeId = resumeId;
		this.filePath = filePath;
		this.uploadDate = uploadDate;
		this.jobSeeker = jobSeeker;
	}

 
    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public JobSeeker getJobSeeker() {
        return jobSeeker;
    }
    

    public LocalDate getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDate uploadDate) {
		this.uploadDate = uploadDate;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }
}
