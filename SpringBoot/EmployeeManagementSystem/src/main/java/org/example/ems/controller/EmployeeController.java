package org.example.ems.controller;

import jakarta.validation.Valid;
import org.example.ems.model.Employee;
import org.example.ems.service.EmployeeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee createEmployee(@Valid @RequestBody Employee employee){
        return  employeeService.createEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable int id) {
        return employeeService.getEmployee(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @Valid @RequestBody Employee employee){
        return  employeeService.updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
    }

    @GetMapping("/department/{dept}")
    public List<Employee> getAllEmployeesByDepartment(@PathVariable String dept){
        return employeeService.getEmployeesByDepartment(dept);
    }
}
