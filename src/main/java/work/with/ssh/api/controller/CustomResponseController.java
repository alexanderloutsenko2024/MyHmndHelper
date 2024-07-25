package work.with.ssh.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import work.with.ssh.api.model.CustomResponse;
import work.with.ssh.service.CustomResponseService;

@RestController
public class CustomResponseController {
    
    private CustomResponseService service;

    @Autowired
    public CustomResponseController(CustomResponseService receivedObjectOfService) {
        this.service = receivedObjectOfService;
    }

    @GetMapping("/get-data")
    public CustomResponse getCustomResponse(@RequestParam String host, @RequestParam String token) {
        System.out.println(" ** inside of controller ** - host received from URL is " + host);
        return service.getCustomService(host, token);
    }
}
