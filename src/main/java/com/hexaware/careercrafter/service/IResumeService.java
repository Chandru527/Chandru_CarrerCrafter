package com.hexaware.careercrafter.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.hexaware.careercrafter.dto.ResumeDto;

public interface IResumeService {
    ResumeDto getResumeById(int id);
    List<ResumeDto> getAllResumes();
    ResumeDto updateResume(int id, ResumeDto resumeDto);
    void deleteResume(int id);
    ResumeDto uploadResume(MultipartFile file, Integer jobSeekerId);
}
