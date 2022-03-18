package org.example.raspberry.controllers;

import org.example.raspberry.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

    private final ProducerService producerService;

    @Autowired
    public ApiController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @GetMapping("/api")
    @ResponseBody
    public Result api() {
        Result result = new Result();
        result.setMin(producerService.findShortestConsecutiveWins());
        result.setMax(producerService.findLongestConsecutiveWins());
        return result;
    }
}
