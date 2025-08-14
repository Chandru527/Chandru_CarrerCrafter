package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.ResumeDto;

/*
 * 
 * Author: Chandru
 * Date: 13-Aug-2025
 * 
 * 
 */


public interface IResumeService {
    ResumeDto createResume(ResumeDto resumeDto);
    ResumeDto getResumeById(int id);
    List<ResumeDto> getAllResumes();
    ResumeDto updateResume(int id, ResumeDto resumeDto);
    void deleteResume(int id);
}
