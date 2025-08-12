package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "job_searches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobSearch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int searchId;

    @ManyToOne
    @JoinColumn(name = "job_seeker_id", nullable = false)
    private JobSeeker jobSeeker;

    @Column(nullable = false, length = 100)
    private String keywords;

    @Column(length = 50)
    private String location;

    @Column(length = 50)
    private String industry;

    @Column(length = 1000)
    private String recommendedJobs;

    @Column(length = 20)
    private String searchDate;
}
