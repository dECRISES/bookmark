package com.decrises.bms.bookmark.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.repository.BookmarkRepository;
import com.decrises.bms.bookmark.repository.dao.BookmarkDao;

@Configuration
@EntityScan("com.decrises.bms.bookmark.repository.entity")
@EnableJpaRepositories("com.decrises.bms.bookmark.repository.dao")
public class JpaAdapterConfig {

  @Bean
  public ObtainBookmark getBookmarkRepository(BookmarkDao bookmarkDao) {
    return new BookmarkRepository(bookmarkDao);
  }
}
