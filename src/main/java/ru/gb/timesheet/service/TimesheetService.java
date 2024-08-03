package ru.gb.timesheet.service;

import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.aspect.Recover;
import ru.gb.timesheet.aspect.Timer;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.repository.ProjectRepository;
import ru.gb.timesheet.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@Timer(level = Level.TRACE)
public class TimesheetService {

    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public TimesheetService(TimesheetRepository repository, ProjectRepository projectRepository) {
        this.timesheetRepository = repository;
        this.projectRepository = projectRepository;
    }

    @Recover(noRecoverFor = {NoSuchElementException.class, IllegalStateException.class})
    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    @Recover(noRecoverFor = {IllegalArgumentException.class})
    public List<Timesheet> findAll() {
        return findAll(null, null);
    }

    @Recover(noRecoverFor = {IllegalArgumentException.class})
    public List<Timesheet> findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        if (createdAtBefore == null && createdAtAfter == null) {
            return timesheetRepository.findAll();
        } else {
            return timesheetRepository.findByCreatedAtBeforeAndCreatedAtAfter(createdAtBefore, createdAtAfter);
        }
    }

    @Recover(noRecoverFor = {IllegalArgumentException.class, NoSuchElementException.class})
    public Timesheet create(Timesheet timesheet) {
        if (Objects.isNull(timesheet.getProjectId())) {
            throw new IllegalArgumentException("projectId must not be null");
        }
        if (projectRepository.findById(Long.valueOf(timesheet.getProjectId())).isEmpty()) {
            throw new NoSuchElementException("'Project' с id = " + timesheet.getProjectId() + " не существует");
        }
        timesheet.setCreatedAt(LocalDate.now());
        return timesheetRepository.save(timesheet);
    }

    @Recover(noRecoverFor = {NoSuchElementException.class})
    public void delete(Long id) {
        if (timesheetRepository.existsById(id)) {
            timesheetRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Timesheet with id=" + id + " does not exist");
        }
    }

    @Recover(noRecoverFor = {IllegalArgumentException.class})
    public List<Timesheet> findByEmployeeId(Long id) {
        return List.of();
    }
}

