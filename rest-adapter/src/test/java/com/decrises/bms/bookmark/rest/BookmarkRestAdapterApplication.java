package com.decrises.bms.bookmark.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;

@SpringBootApplication
@ComponentScan(basePackages = "com.decrises.bms.bookmark")
public class BookmarkRestAdapterApplication {

  @MockBean
  private RequestBookmark requestBookmark;

  public static void main(String[] args) {
    SpringApplication.run(BookmarkRestAdapterApplication.class, args);
  }
}
