package com.hexaware.careercrafter.entities;

import jakarta.persistence.*;

/*
 * Class Name: Employee
 * Description: This class represents an employee entity
 *              with details like name, email, and role within the company.
 * Author: Chandru
 * Date: 13-Aug-2025
 */

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employeeId;

    private String companyName;
    private String companyDescription;
    private String position;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User user;

   
    public Employee() {}
    

    public Employee(int employeeId, String companyName, String companyDescription, String position, User user) {
		
		this.employeeId = employeeId;
		this.companyName = companyName;
		this.companyDescription = companyDescription;
		this.position = position;
		this.user = user;
	}


    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
