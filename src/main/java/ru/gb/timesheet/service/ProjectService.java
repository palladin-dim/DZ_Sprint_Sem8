package ru.gb.timesheet.service;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.aspect.Recover;
import ru.gb.timesheet.aspect.Timer;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Timer(level = Level.TRACE)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, TimesheetRepository timesheetRepository) {
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    @Recover(noRecoverFor = {NoSuchElementException.class})
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    @Recover(noRecoverFor = {IllegalStateException.class})
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Recover(noRecoverFor = {IllegalArgumentException.class})
    public Project create(Project project) {
        return projectRepository.save(project);
    }

    @Recover(noRecoverFor = {IllegalStateException.class})
    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

    @Recover(noRecoverFor = {NoSuchElementException.class})
    public List<Timesheet> getTimesheets(Long id) {
        if (projectRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Project с id = " + id + " не существует");
        }
        return timesheetRepository.findByProjectId(id);
    }
}


