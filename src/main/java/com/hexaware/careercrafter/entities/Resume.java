package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resumeId;

    private String filePath;

    @OneToOne
    @JoinColumn(name = "job_seeker_id", referencedColumnName = "jobSeekerId")
    private JobSeeker jobSeeker;

    
    public Resume() {}

    public Resume(int resumeId, String filePath, JobSeeker jobSeeker) {
        this.resumeId = resumeId;
        this.filePath = filePath;
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

    public void setJobSeeker(JobSeeker jobSeeker) {
        this.jobSeeker = jobSeeker;
    }
}
