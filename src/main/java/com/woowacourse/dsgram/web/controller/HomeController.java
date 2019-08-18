package com.woowacourse.dsgram.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);
    @Autowired
    ApplicationContext applicationContext;

    @GetMapping("/")
    public String showMainPage() throws IOException {

        Resource resource = new FileSystemResource("/");
        log.info("{}  ,{},{}", resource.getFilename(), resource.getURL(), resource.getFile().getPath());
        log.info("exists? {}", resource.exists());
        return "index";
    }
}
