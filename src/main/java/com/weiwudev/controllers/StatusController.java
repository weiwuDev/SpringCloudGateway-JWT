package com.weiwudev.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StatusController {

    @GetMapping("/check")
    public Mono<String> checkStatus(){
        return Mono.just("I'm alive");
    }



    @GetMapping("/checkno")
    public Mono<String> checkStatusNoAuth(){
        return Mono.just("I'm alive two");
    }
}
