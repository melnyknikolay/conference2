package com.app.controller;

import com.app.producer.MyQueueProducer;
import com.app.producer.MyQueueProducerWithHeader;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
    @Autowired
    private MyQueueProducer myQueueProducer;
    @Autowired
    private MyQueueProducerWithHeader queueProducerWithHeader;

    @GetMapping("/doAction")
    public void doAction() {
        System.out.println("##########Producer. Data was published####");
        myQueueProducer.send("pubish to QUEUE; " + System.currentTimeMillis());

    }

    @GetMapping("/doActionWithHeader")
    public void doActionWithHeader() {
        System.out.println("##########Producer. Data was published####");
        queueProducerWithHeader.send("pubish to QUEUE; " + System.currentTimeMillis());
    }

}
