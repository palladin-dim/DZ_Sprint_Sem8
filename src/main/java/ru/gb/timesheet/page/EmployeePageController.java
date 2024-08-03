package ru.gb.timesheet.page;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.timesheet.service.EmployeeService;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/home/employees")
public class EmployeePageController {

    private final EmployeeService employeeService;

    public EmployeePageController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees";
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id).orElseThrow(() -> new NoSuchElementException("Запись не найдена")));
        return "employee-page";
    }
}
