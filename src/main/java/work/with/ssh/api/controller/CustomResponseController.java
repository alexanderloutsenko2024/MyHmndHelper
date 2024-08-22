package work.with.ssh.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import work.with.ssh.api.model.CustomResponse;
import work.with.ssh.service.CustomResponseService;

/*
 * based on example found in https://www.youtube.com/watch?v=Zo9xQzibp4Y video
 */
@RestController
public class CustomResponseController {
    
    private CustomResponseService service;

    @Autowired
    public CustomResponseController(CustomResponseService receivedObjectOfService) {
        this.service = receivedObjectOfService;
    }

    @GetMapping("/get-node")
    public CustomResponse getSingleCustomResponse(@RequestParam String host, @RequestParam String token) {
        System.out.println(" ** inside of controller ** - host received from URL is " + host);
        return service.getSingleCustomResponse(host, token);
    }

    // that was implemented with help of https://stackoverflow.com/questions/41719142/how-to-return-a-set-of-objects-with-spring-boot article
    @RequestMapping(value = "/get-all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)//@GetMapping("/get-all")
    public List<CustomResponse> getMultipleCustomResponses(@RequestParam String token) {
        System.out.println(" ** inside of controller ** - 'all' received from URL ");
        return service.getMultipleCustomResponses(token);
    }
}
