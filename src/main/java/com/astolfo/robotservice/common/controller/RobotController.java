package com.astolfo.robotservice.common.controller;

import com.astolfo.robotservice.common.infrastructure.response.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/common")
@RestController
public class RobotController {

    @GetMapping("/say")
    public R<String> say() {
        return R.success("Hello world!");
    }

}
