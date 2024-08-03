package ru.gb.timesheet.rest;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.timesheet.model.Employee;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.service.EmployeeService;
import ru.gb.timesheet.service.TimesheetService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final TimesheetService timesheetService;

    public EmployeeController(EmployeeService employeeService, TimesheetService timesheetService) {
        this.employeeService = employeeService;
        this.timesheetService = timesheetService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.create(employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/timesheets")
    public ResponseEntity<List<Timesheet>> getTimesheets(@PathVariable Long id) {
        Employee employee = employeeService.findById(id).orElseThrow(() -> new NoSuchElementException("Запись с id=" + id + " не существует"));
        return ResponseEntity.ok(timesheetService.findByEmployeeId(id));
    }
}
