package com.example.demo.Controllers;

import com.example.demo.Model.Worker;
import com.example.demo.Service.SupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SupController {
    @Autowired
    private SupService supService;
    @GetMapping("/getInactiveWorkers")
    public List<Worker> getInactiveWorkers(){
        return supService.getInactiveWorkers();
    }

    @PostMapping("/reActivateWorker/{workerId}")
    public String reActivateWorker(@PathVariable Long workerId){
        return supService.reactivateWorker(workerId);
    }


}
