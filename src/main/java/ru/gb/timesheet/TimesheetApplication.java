package ru.gb.timesheet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import ru.gb.timesheet.model.*;
import ru.gb.timesheet.repository.*;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class TimesheetApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(TimesheetApplication.class, args);

        // Создание пользователей и ролей
        createUsersAndRoles(ctx);
    }

    private static void createUsersAndRoles(ApplicationContext ctx) {
        UserRepository userRepository = ctx.getBean(UserRepository.class);
        UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);

        // Создание пользователей
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin
        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$.dlnBAYq6sOUumn3jtG.AepxdSwGxJ8xA2iAPoCHSH61Vjl.JbIfq"); // user
        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon
        admin = userRepository.save(admin);
        user = userRepository.save(user);
        anonymous = userRepository.save(anonymous);

        // Создание ролей
        createRole(userRoleRepository, admin.getId(), Role.ADMIN.getName());
        createRole(userRoleRepository, admin.getId(), Role.USER.getName());
        createRole(userRoleRepository, user.getId(), Role.USER.getName());
    }

    private static void createRole(UserRoleRepository userRoleRepository, Long userId, String roleName) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleName(roleName);
        userRoleRepository.save(userRole);
    }


    @Bean
    public CommandLineRunner proj(ProjectRepository projectRepo, TimesheetRepository timesheetRepo) {
        return (args) -> {
            for (int i = 1; i <= 5; i++) {
                Project project = new Project();
                project.setName("Project #" + i);
                projectRepo.save(project);
            }

            LocalDate createdAt = LocalDate.now();
            for (int i = 1; i <= 10; i++) {
                createdAt = createdAt.plusDays(1);
                Timesheet timesheet = new Timesheet();
                timesheet.setProjectId(ThreadLocalRandom.current().nextLong(1, 6));
                timesheet.setCreatedAt(createdAt);
                timesheet.setMinutes(ThreadLocalRandom.current().nextInt(100, 1000));
                timesheetRepo.save(timesheet);
            }
        };
    }


    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository) {
        return args -> {
            employeeRepository.save(new Employee("Петр", "Сысоев", 30, "Development"));
            employeeRepository.save(new Employee("Марина", "Карнилова", 25, "Marketing"));
            employeeRepository.save(new Employee("Роберт", "Панов", 40, "Sales"));
            employeeRepository.save(new Employee("Елена", "Павлова", 35, "HR"));
            employeeRepository.save(new Employee("Евгений", "Коньков", 28, "Development"));
            employeeRepository.save(new Employee("Лариса", "Нилина", 32, "Finance"));
            employeeRepository.save(new Employee("Александр", "Аничковский", 45, "Management"));
        };
    }

}
