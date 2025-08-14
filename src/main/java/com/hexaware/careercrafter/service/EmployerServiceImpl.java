package com.hexaware.careercrafter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.careercrafter.dto.EmployerDto;
import com.hexaware.careercrafter.entities.Employer;
import com.hexaware.careercrafter.entities.User;
import com.hexaware.careercrafter.exception.InvalidRequestException;
import com.hexaware.careercrafter.exception.ResourceNotFoundException;
import com.hexaware.careercrafter.repository.IEmployerRepo;
import com.hexaware.careercrafter.repository.IUserRepo;

/*
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Service
public class EmployerServiceImpl implements IEmployerService {

    private static final Logger logger = LoggerFactory.getLogger(EmployerServiceImpl.class); // 🔹 Logger

    @Autowired
    private IEmployerRepo employerRepo;

    @Autowired
    private IUserRepo userRepo;

    @Override
    public EmployerDto createEmployer(EmployerDto employerDto) {
        logger.info("Creating new employer for userId: {}", employerDto.getUserId());

        if (employerDto.getCompanyName() == null || employerDto.getCompanyName().isBlank()) {
            logger.warn("Company name is missing");
            throw new InvalidRequestException("Company name is required");
        }
        if (employerDto.getCompanyDescription() == null || employerDto.getCompanyDescription().isBlank()) {
            logger.warn("Company description is missing");
            throw new InvalidRequestException("Company description is required");
        }
        if (employerDto.getUserId() == null) {
            logger.warn("User ID is missing");
            throw new InvalidRequestException("User ID is required");
        }

        User user = userRepo.findById(employerDto.getUserId())
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", employerDto.getUserId());
                    return new ResourceNotFoundException("User not found with id: " + employerDto.getUserId());
                });

        Employer savedEmployer = employerRepo.save(mapToEntity(employerDto, user));
        logger.debug("Employer saved successfully: {}", savedEmployer);
        return mapToDto(savedEmployer);
    }

    @Override
    public EmployerDto getEmployerById(int id) {
        logger.info("Fetching employer with id: {}", id);
        Employer employer = employerRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employer not found with id: {}", id);
                    return new ResourceNotFoundException("Employer not found with id: " + id);
                });
        return mapToDto(employer);
    }

    @Override
    public List<EmployerDto> getAllEmployers() {
        logger.info("Fetching all employers");
        List<EmployerDto> employers = employerRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        logger.debug("Total employers found: {}", employers.size());
        return employers;
    }

    @Override
    public EmployerDto updateEmployer(int id, EmployerDto employerDto) {
        logger.info("Updating employer with id: {}", id);

        Employer existingEmployer = employerRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Employer not found with id: {}", id);
                    return new ResourceNotFoundException("Employer not found with id: " + id);
                });

        existingEmployer.setCompanyName(employerDto.getCompanyName());
        existingEmployer.setCompanyDescription(employerDto.getCompanyDescription());
        existingEmployer.setPosition(employerDto.getPosition());

        if (employerDto.getUserId() != null) {
            User user = userRepo.findById(employerDto.getUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found with id: {}", employerDto.getUserId());
                        return new ResourceNotFoundException("User not found with id: " + employerDto.getUserId());
                    });
            existingEmployer.setUser(user);
        }

        Employer updatedEmployer = employerRepo.save(existingEmployer);
        logger.debug("Employer updated successfully: {}", updatedEmployer);
        return mapToDto(updatedEmployer);
    }

    @Override
    public void deleteEmployer(int id) {
        logger.info("Deleting employer with id: {}", id);
        if (!employerRepo.existsById(id)) {
            logger.error("Employer not found with id: {}", id);
            throw new ResourceNotFoundException("Employer not found with id: " + id);
        }
        employerRepo.deleteById(id);
        logger.info("Employer deleted successfully with id: {}", id);
    }

    private EmployerDto mapToDto(Employer employer) {
        EmployerDto dto = new EmployerDto();
        dto.setEmployerId(employer.getEmployerId());
        dto.setCompanyName(employer.getCompanyName());
        dto.setCompanyDescription(employer.getCompanyDescription());
        dto.setPosition(employer.getPosition());
        dto.setUserId(employer.getUser() != null ? employer.getUser().getUserId() : null);
        return dto;
    }

    private Employer mapToEntity(EmployerDto dto, User user) {
        Employer employer = new Employer();
        employer.setEmployerId(dto.getEmployerId());
        employer.setCompanyName(dto.getCompanyName());
        employer.setCompanyDescription(dto.getCompanyDescription());
        employer.setPosition(dto.getPosition());
        employer.setUser(user);
        return employer;
    }
}
