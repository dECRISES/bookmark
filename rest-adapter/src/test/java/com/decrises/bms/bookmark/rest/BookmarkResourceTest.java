package com.decrises.bms.bookmark.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.decrises.bms.bookmark.domain.exception.BookmarkNotFoundException;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;
import com.decrises.bms.bookmark.rest.exception.BookmarkExceptionResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = BookmarkRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class BookmarkResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/bookmarks";
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RequestBookmark requestBookmark;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give bookmarks when asked for bookmarks with the support of domain stub")
  public void obtainBookmarksFromDomainStub() {
    // Given
    Bookmark bookmark = Bookmark.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestBookmark.getBookmarks())
        .thenReturn(BookmarkInfo.builder().bookmarks(List.of(bookmark)).build());
    // When
    String url = LOCALHOST + port + API_URI;
    ResponseEntity<BookmarkInfo> responseEntity = restTemplate.getForEntity(url, BookmarkInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getBookmarks()).isNotEmpty().extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName("should give the bookmark when asked for an bookmark by code with the support of domain stub")
  public void obtainBookmarkByCodeFromDomainStub() {
    // Given
    Long code = 1L;
    String description = "Johnny Johnny Yes Papa !!";
    Bookmark bookmark = Bookmark.builder().code(code).description(description).build();
    Mockito.lenient().when(requestBookmark.getBookmarkByCode(code)).thenReturn(bookmark);
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<Bookmark> responseEntity = restTemplate.getForEntity(url, Bookmark.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(bookmark);
  }

  @Test
  @DisplayName("should give exception when asked for an bookmark by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnBookmarkByCodeFromDomainStub() {
    // Given
    Long code = -1000L;
    Mockito.lenient().when(requestBookmark.getBookmarkByCode(code)).thenThrow(new
        BookmarkNotFoundException(code));
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<BookmarkExceptionResponse> responseEntity = restTemplate
        .getForEntity(url, BookmarkExceptionResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(BookmarkExceptionResponse.builder()
        .message("Bookmark with code " + code + " does not exist").path(API_URI + "/" + code)
        .build());
  }
}
