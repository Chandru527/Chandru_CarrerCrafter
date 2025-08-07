package com.hexaware.careercrafter.service;

import com.hexaware.careercrafter.entities.Resume;
import java.util.List;

public interface IResumeService {
    Resume saveResume(Resume resume);
    Resume getResumeById(int id);
    List<Resume> getAllResumes();
    void deleteResume(int id);
}
