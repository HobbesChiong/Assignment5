package ca.myapp.controllers;

import model.Manager;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final Manager manager = new Manager();

    @GetMapping("/api/dump-model")
    @ResponseStatus(HttpStatus.OK)
    public void dumpModel() {
        manager.dumpModel();
    }

}
