package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;
import lombok.*;

/*
 * Class Name: JobSearch
 * Description: This class represents a job search entity
 *              containing search preferences and criteria set by a job seeker.
 * Author: Chandru
 * Date: 13-Aug-2025
 */

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
