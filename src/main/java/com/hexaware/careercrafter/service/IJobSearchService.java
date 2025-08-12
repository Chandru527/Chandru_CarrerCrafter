package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.dto.JobListingDto;
import com.hexaware.careercrafter.dto.JobSearchDto;
import java.util.List;

public interface IJobSearchService {
	
    JobSearchDto createSearch(JobSearchDto dto);
    JobSearchDto getSearchById(int id);
    List<JobSearchDto> getSearchesByJobSeeker(int jobSeekerId);
    JobSearchDto updateSearch(JobSearchDto dto);
    void deleteSearch(int id);
    List<JobListingDto> recommendJobs(JobSearchDto dto);
}
