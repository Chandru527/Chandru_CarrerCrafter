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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobSearchServiceImpl implements IJobSearchService {

    private static final Logger logger = LoggerFactory.getLogger(JobSearchServiceImpl.class); // 🔹 Logger

    @Autowired
    private IJobSearchRepo jobSearchRepo;

    @Autowired
    private IJobSeekerRepo jobSeekerRepo;

    @Autowired
    private IJobListingRepo jobListingRepo;

    @Override
    public JobSearchDto createSearch(JobSearchDto dto) {
        logger.info("Creating JobSearch for JobSeekerId: {}", dto.getJobSeekerId());
        JobSeeker jobSeeker = jobSeekerRepo.findById(dto.getJobSeekerId())
                .orElseThrow(() -> {
                    logger.error("JobSeeker not found with id: {}", dto.getJobSeekerId());
                    return new ResourceNotFoundException("Job Seeker not found");
                });

        JobSearch search = new JobSearch();
        search.setJobSeeker(jobSeeker);
        search.setKeywords(dto.getKeywords());
        search.setLocation(dto.getLocation());
        search.setIndustry(dto.getIndustry());
        search.setRecommendedJobs(dto.getRecommendedJobs());
        search.setSearchDate(LocalDate.now().toString());

        JobSearch saved = jobSearchRepo.save(search);
        logger.debug("JobSearch saved successfully: {}", saved);
        return mapToDto(saved);
    }

    @Override
    public JobSearchDto getSearchById(int id) {
        logger.info("Fetching JobSearch by id: {}", id);
        JobSearch search = jobSearchRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Job search not found with id: {}", id);
                    return new ResourceNotFoundException("Job search not found");
                });
        return mapToDto(search);
    }

    @Override
    public List<JobSearchDto> getSearchesByJobSeeker(int jobSeekerId) {
        logger.info("Fetching JobSearches for JobSeekerId: {}", jobSeekerId);
        List<JobSearchDto> searches = jobSearchRepo.findByJobSeeker_JobSeekerId(jobSeekerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total JobSearches found: {}", searches.size());
        return searches;
    }

    @Override
    public JobSearchDto updateSearch(JobSearchDto dto) {
        logger.info("Updating JobSearch with id: {}", dto.getSearchId());
        JobSearch existing = jobSearchRepo.findById(dto.getSearchId())
                .orElseThrow(() -> {
                    logger.error("Job search not found with id: {}", dto.getSearchId());
                    return new ResourceNotFoundException("Job search not found");
                });

        existing.setKeywords(dto.getKeywords());
        existing.setLocation(dto.getLocation());
        existing.setIndustry(dto.getIndustry());
        existing.setRecommendedJobs(dto.getRecommendedJobs());

        JobSearch updated = jobSearchRepo.save(existing);
        logger.debug("JobSearch updated successfully: {}", updated);
        return mapToDto(updated);
    }

    @Override
    public void deleteSearch(int id) {
        logger.info("Deleting JobSearch with id: {}", id);
        if (!jobSearchRepo.existsById(id)) {
            logger.error("JobSearch not found with id: {}", id);
            throw new ResourceNotFoundException("Job search not found");
        }
        jobSearchRepo.deleteById(id);
        logger.info("JobSearch deleted successfully with id: {}", id);
    }

    @Override
    public List<JobListingDto> recommendJobs(JobSearchDto dto) {
        logger.info("Recommending jobs for keywords: '{}' and location: {}",
                dto.getKeywords(), dto.getLocation());

        List<JobListingDto> recommended = jobListingRepo.findAll().stream()
                .filter(job -> job.getTitle().toLowerCase().contains(dto.getKeywords().toLowerCase())
                        || job.getDescription().toLowerCase().contains(dto.getKeywords().toLowerCase()))
                .filter(job -> dto.getLocation() == null || job.getLocation().equalsIgnoreCase(dto.getLocation()))
                .map(this::mapJobToDto)
                .collect(Collectors.toList());

        logger.debug("Total recommended jobs: {}", recommended.size());
        return recommended;
    }

   
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
