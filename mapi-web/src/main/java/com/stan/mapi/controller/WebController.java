package com.stan.mapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stan.dubbobaseinterface.service.WebEntryService;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Reference(version = "1.0.0")
    WebEntryService webEntryService;

    // http://localhost:8080/mapi/iotbmp.test?id=123
    @RequestMapping("/mapi/{apiName}")
    public String entry(@PathVariable String apiName, @RequestParam String id){
        System.out.println("[WebController.entry] apiName: " + apiName + " ,id=" + id);

        String[] arr = apiName.split("\\.");
        String groupName = arr[0];
        String methodName = arr[1];

//                return "11111" + arr;
        return webEntryService.entry(groupName, methodName, id);
    }

    @RequestMapping("/gen-api/{apiName}")
    public String entryGeneric(@PathVariable String apiName, @RequestParam String id){
        System.out.println("[WebController.entry] apiName: " + apiName + " ,id=" + id);

        String[] arr = apiName.split("\\.");
        String groupName = arr[0];
        String methodName = arr[1];

        return webEntryService.entryGeneric(groupName, methodName, id);
    }
}
