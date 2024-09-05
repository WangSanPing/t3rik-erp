package com.t3rik.print.printserver;

import jakarta.annotation.Resource;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class PrintServerOpenListener implements ApplicationRunner {

    @Resource
    private PrinterServer printerServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        printerServer.start();
    }

}
