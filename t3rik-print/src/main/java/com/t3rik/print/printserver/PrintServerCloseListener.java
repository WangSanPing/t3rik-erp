package com.t3rik.print.printserver;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;



@Component
public class PrintServerCloseListener implements DisposableBean {

    @Resource
    private PrinterServer printerServer;

    @Override
    public void destroy() throws Exception {
        printerServer.stop();
    }
}
