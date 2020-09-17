package com.decrises.bms.bookmark.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.repository.dao.BookmarkDao;

@SpringBootApplication
public class BookmarkJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookmarkJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  static class BookmarkJpaTestConfig {

    @Bean
    public ObtainBookmark getObtainBookmarkRepository(BookmarkDao bookmarkDao) {
      return new BookmarkRepository(bookmarkDao);
    }
  }
}
