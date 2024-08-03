package ru.gb.timesheet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.timesheet.service.ApiService;

@RestController
public class ApiController {

    private final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/data")
    public String getData(@RequestParam String username, @RequestParam String password, @RequestParam String url) {
        return apiService.makeAuthenticatedRequest(username, password, url);
    }
}
