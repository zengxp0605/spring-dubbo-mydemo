package com.stan.mapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stan.dubbobaseinterface.service.WebEntryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Reference(version = "1.0.0")
    WebEntryService webEntryService;

    // http://localhost:8080/mapi/iotbmp?mt=test&id=123
    @GetMapping("/mapi/{groupName}")
    public String entry(@PathVariable String groupName, @RequestParam String mt, @RequestParam String id){
        System.out.println("[WebController.entry] groupName: " + groupName + " ,mt=" + mt + " ,id=" + id);

        return webEntryService.entry(groupName, mt, id);
    }
}
