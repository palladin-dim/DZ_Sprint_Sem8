package ru.gb.timesheet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.timesheet.model.Project;
import ru.gb.timesheet.model.Timesheet;
import ru.gb.timesheet.page.TimesheetPageDto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public List<TimesheetPageDto> findAll() {
        return timesheetService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.findById(id)
                .map(this::convert);
    }

    private TimesheetPageDto convert(Timesheet timesheet) {
        Project project = projectService.findById(timesheet.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("Проект не найден"));

        TimesheetPageDto timesheetPageDto = new TimesheetPageDto();
        timesheetPageDto.setProjectName(project.getName());
        timesheetPageDto.setId(String.valueOf(timesheet.getId()));
        timesheetPageDto.setProjectId(timesheet.getProjectId());
        timesheetPageDto.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageDto.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));

        return timesheetPageDto;
    }
}