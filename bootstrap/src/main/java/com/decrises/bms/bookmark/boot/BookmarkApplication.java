package com.decrises.bms.bookmark.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.decrises.bms.bookmark")
public class BookmarkApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookmarkApplication.class, args);
  }
}
