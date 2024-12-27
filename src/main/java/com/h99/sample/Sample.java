package com.h99.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Sample {

    @RequestMapping("/welcome")
    public String greeting(){
        return "welcome!!";
    }
}
