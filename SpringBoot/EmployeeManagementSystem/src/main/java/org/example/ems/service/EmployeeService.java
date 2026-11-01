package org.example.ems.service;

import org.example.ems.model.Employee;
import org.example.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(int id, Employee employee) {
        Employee existingEmployee = getEmployee(id);

        existingEmployee.setEmployeeName(employee.getEmployeeName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setAge(employee.getAge());
        existingEmployee.setSalary(employee.getSalary());

        return existingEmployee;
    }

    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    public Employee getEmployee(Integer id) {
        return employeeRepository.getReferenceById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public List<Employee> getEmployeesByDepartment(String dept) {
        return employeeRepository.findByDepartment(dept);
    }
}
