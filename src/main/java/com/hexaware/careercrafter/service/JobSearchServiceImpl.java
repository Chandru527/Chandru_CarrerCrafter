package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import com.hexaware.careercrafter.entities.JobListing;
import com.hexaware.careercrafter.entities.JobSearch;
import com.hexaware.careercrafter.entities.JobSeeker;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IJobListingRepo;
import com.hexaware.careercrafter.repository.IJobSearchRepo;
import com.hexaware.careercrafter.repository.IJobSeekerRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSearchServiceImpl implements IJobSearchService {

    @Autowired
    private IJobSearchRepo jobSearchRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Override
    public JobSearchDto createSearch(JobSearchDto dto) {
        JobSeeker jobSeeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> new ResourceNotFoundException("Job Seeker not found"));

        JobSearch search = new JobSearch();
        search.setJobSeeker(jobSeeker);
        search.setKeywords(dto.getKeywords());
        search.setLocation(dto.getLocation());
        search.setIndustry(dto.getIndustry());
        search.setRecommendedJobs(dto.getRecommendedJobs()); // optional
        search.setSearchDate(LocalDate.now().toString());

        JobSearch saved = jobSearchRepo.save(search);
        return mapToDto(saved);
    }

    @Override
    public JobSearchDto getSearchById(int id) {
        JobSearch search = jobSearchRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job search not found"));
        return mapToDto(search);
    }

    @Override
    public List<JobSearchDto> getSearchesByJobSeeker(int jobSeekerId) {
        return jobSearchRepo.findByJobSeeker_JobSeekerId(jobSeekerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public JobSearchDto updateSearch(JobSearchDto dto) {
        JobSearch existing = jobSearchRepo.findById(dto.getSearchId())
                .orElseThrow(() -> new ResourceNotFoundException("Job search not found"));

        existing.setKeywords(dto.getKeywords());
        existing.setLocation(dto.getLocation());
        existing.setIndustry(dto.getIndustry());
        existing.setRecommendedJobs(dto.getRecommendedJobs());

        JobSearch updated = jobSearchRepo.save(existing);
        return mapToDto(updated);
    }

    @Override
    public void deleteSearch(int id) {
        if (!jobSearchRepo.existsById(id)) {
            throw new ResourceNotFoundException("Job search not found");
        }
        jobSearchRepo.deleteById(id);
    }

    @Override
    public List<JobListingDto> recommendJobs(JobSearchDto dto) {
        // Find matching jobs without saving to DB
        return jobListingRepo.findAll().stream()
                .filter(job -> job.getTitle().toLowerCase().contains(dto.getKeywords().toLowerCase())
                        || job.getDescription().toLowerCase().contains(dto.getKeywords().toLowerCase()))
                .filter(job -> dto.getLocation() == null || job.getLocation().equalsIgnoreCase(dto.getLocation()))
                .map(this::mapJobToDto)
                .collect(Collectors.toList());
    }

    // --- Mapping Helpers ---
    private JobSearchDto mapToDto(JobSearch search) {
        JobSearchDto dto = new JobSearchDto();
        dto.setSearchId(search.getSearchId());
        dto.setJobSeekerId(search.getJobSeeker().getJobSeekerId());
        dto.setKeywords(search.getKeywords());
        dto.setLocation(search.getLocation());
        dto.setIndustry(search.getIndustry());
        dto.setRecommendedJobs(search.getRecommendedJobs());
        dto.setSearchDate(search.getSearchDate());
        return dto;
    }

    private JobListingDto mapJobToDto(JobListing job) {
        JobListingDto dto = new JobListingDto();
        dto.setJobListingId(job.getJobListingId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setLocation(job.getLocation());
        dto.setSalary(job.getSalary());
        return dto;
    }
}
