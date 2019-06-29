package com.dev.coding.basic.controller;


import com.dev.coding.basic.entity.Employee;
import com.dev.coding.basic.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/company")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping("/employees")
    public ResponseEntity create(@Valid @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.save(employee));
    }

    @GetMapping("employee/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (!employee.isPresent()) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(employee.get());
    }

    @PutMapping("employee/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @Valid @RequestBody Employee employee) {
        Optional<Employee> employeeOptional = employeeService.findById(id);


        if (!employeeOptional.isPresent())
            return ResponseEntity.notFound().build();

        employee.setId(id);
        employeeService.save(employee);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        if (!employeeService.findById(id).isPresent()) {
            ResponseEntity.badRequest().build();
        }
        employeeService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
