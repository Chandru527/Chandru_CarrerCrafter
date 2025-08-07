package com.hexaware.careercrafter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.entities.Resume;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IResumeRepo;

@Service
public class ResumeServiceImpl implements IResumeService {

    @Autowired
    private IResumeRepo resumeRepo;

    @Override
    public Resume saveResume(Resume resume) {
        if (resume.getFilePath() == null || resume.getFilePath().isBlank()) {
            throw new InvalidRequestException("File path is required");
        }
        return resumeRepo.save(resume);
    }

    @Override
    public Resume getResumeById(int id) {
        return resumeRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found with id: " + id));
    }

    @Override
    public List<Resume> getAllResumes() {
        return resumeRepo.findAll();
    }

    @Override
    public void deleteResume(int id) {
        if (!resumeRepo.existsById(id)) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepo.deleteById(id);
    }
}
