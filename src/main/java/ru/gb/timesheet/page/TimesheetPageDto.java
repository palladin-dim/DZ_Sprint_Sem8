package ru.gb.timesheet.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * Класс (DTO Data Transfer Object), который описывает параметры для шаблоново HTML-страниц.
 * Т.е. он нужен для передачи параметров внутрь thymeleaf в тех контроллерах, которые сразу отдают HTML-страницы.
 */

// Класс DTO для передачи данных на страницу учета рабочего времени
// Геттеры и сеттеры для каждого поля
@Setter
@Getter
@Data
public class TimesheetPageDto {
    // Идентификатор записи таймшита
    private String id;

    // Название проекта, к которому относится запись
    private String projectName;

    // Идентификатор проекта
    private Long projectId;

    // Количество минут, потраченных на проект
    private String minutes;

    // Дата создания записи в формате строки
    private String createdAt;

    // Конструктор по умолчанию
    public TimesheetPageDto() {
    }

//    public TimesheetPageDto(String id, String projectName, Long projectId, String minutes, String createdAt) {
//        this.id = id;
//        this.projectName = projectName;
//        this.projectId = projectId;
//        this.minutes = minutes;
//        this.createdAt = createdAt;
//    }
//
//    @Override
//    public String toString() {
//        return "TimesheetPageDto{" +
//                "id='" + id + '\'' +
//                ", projectName='" + projectName + '\'' +
//                ", projectId=" + projectId +
//                ", minutes='" + minutes + '\'' +
//                ", createdAt='" + createdAt + '\'' +
//                '}';
//    }

}
