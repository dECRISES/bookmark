package com.decrises.bms.bookmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import com.decrises.bms.bookmark.domain.BookmarkDomain;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;
import com.decrises.bms.bookmark.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class BookmarkE2EApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookmarkE2EApplication.class);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  static class BookmarkConfig {

    @Bean
    public RequestBookmark getRequestBookmark(final ObtainBookmark obtainBookmark) {
      return new BookmarkDomain(obtainBookmark);
    }
  }
}
