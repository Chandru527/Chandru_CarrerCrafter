package com.hexaware.careercrafter.service;

import java.util.List;
import com.hexaware.careercrafter.dto.ResumeDto;

public interface IResumeService {
    ResumeDto createResume(ResumeDto resumeDto);
    ResumeDto getResumeById(int id);
    List<ResumeDto> getAllResumes();
    ResumeDto updateResume(int id, ResumeDto resumeDto);
    void deleteResume(int id);
}
