package com.kelaniya.uni.employeebackend.controller;

import com.kelaniya.uni.employeebackend.exception.ResourceNotFoundException;
import com.kelaniya.uni.employeebackend.model.Employee;
import com.kelaniya.uni.employeebackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins="http://localhost:3000/")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    //get all employee
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();

    }
    //create Employee Rest API
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){

        return employeeRepository.save(employee);
    }
    //get emplyee by ID
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable long id){

        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with given ID:"+id));

        return ResponseEntity.ok(employee);
    }

    //Update Employee

        @PutMapping("/employees/{id}")
        public ResponseEntity<Employee> updateEmployee(@PathVariable long id,@RequestBody Employee employeeDetails){

            Employee updateEmployee=employeeRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Employee not exist with given ID:"+id));

            updateEmployee.setFirstname(employeeDetails.getFirstname());
            updateEmployee.setLastname(employeeDetails.getLastname());
            updateEmployee.setEmailId(employeeDetails.getEmailId());

            employeeRepository.save(updateEmployee);

            return ResponseEntity.ok(updateEmployee);
        }
        //Delete Employee


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable long id){
        Employee employee=employeeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Employee not exist with given ID:"+id));

        employeeRepository.delete(employee);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
