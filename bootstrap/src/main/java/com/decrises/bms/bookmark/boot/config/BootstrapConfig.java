package com.decrises.bms.bookmark.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.decrises.bms.bookmark.domain.BookmarkDomain;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;
import com.decrises.bms.bookmark.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestBookmark getRequestBookmark(ObtainBookmark obtainBookmark) {
    return new BookmarkDomain(obtainBookmark);
  }
}
