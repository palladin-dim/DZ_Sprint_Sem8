package ru.gb.timesheet.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.service.ProjectService;

import java.util.NoSuchElementException;


@Controller
@RequestMapping("/home/projects")
public class ProjectPageController {

    private final ProjectService projectService;

    public ProjectPageController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{id}")
    public String getProjectPage(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id).orElseThrow(() -> new NoSuchElementException("Проект не найден"));
        model.addAttribute("project", project);
        return "project-page.html";
    }
}
