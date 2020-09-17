package com.decrises.bms.bookmark.cucumber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.cucumber.java8.HookNoArgsBody;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.decrises.bms.bookmark.BookmarkE2EApplication;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;
import com.decrises.bms.bookmark.repository.dao.BookmarkDao;
import com.decrises.bms.bookmark.repository.entity.BookmarkEntity;
import com.decrises.bms.bookmark.rest.exception.BookmarkExceptionResponse;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BookmarkE2EApplication.class, webEnvironment = RANDOM_PORT)
@CucumberContextConfiguration
public class BookmarkStepDef implements En {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/bookmarks";
  @LocalServerPort
  private int port;
  private ResponseEntity responseEntity;

  public BookmarkStepDef(TestRestTemplate restTemplate, BookmarkDao bookmarkDao) {

    DataTableType(
        (Map<String, String> row) -> Bookmark.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description")).build());
    DataTableType(
        (Map<String, String> row) -> BookmarkEntity.builder().code(Long.parseLong(row.get("code")))
            .description(row.get("description"))
            .build());

    Before((HookNoArgsBody) bookmarkDao::deleteAll);
    After((HookNoArgsBody) bookmarkDao::deleteAll);

    Given("the following bookmarks exists in the library", (DataTable dataTable) -> {
      List<BookmarkEntity> poems = dataTable.asList(BookmarkEntity.class);
      bookmarkDao.saveAll(poems);
    });

    When("user requests for all bookmarks", () -> {
      String url = LOCALHOST + port + API_URI;
      responseEntity = restTemplate.getForEntity(url, BookmarkInfo.class);
    });

    When("user requests for bookmarks by code {string}", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, Bookmark.class);
    });

    When("user requests for bookmarks by id {string} that does not exists", (String code) -> {
      String url = LOCALHOST + port + API_URI + "/" + code;
      responseEntity = restTemplate.getForEntity(url, BookmarkExceptionResponse.class);
    });

    Then("the user gets an exception {string}", (String exception) -> {
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      assertThat(body).isInstanceOf(BookmarkExceptionResponse.class);
      assertThat(((BookmarkExceptionResponse) body).getMessage()).isEqualTo(exception);
    });

    Then("the user gets the following bookmarks", (DataTable dataTable) -> {
      List<Bookmark> expectedBookmarks = dataTable.asList(Bookmark.class);
      assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
      Object body = responseEntity.getBody();
      assertThat(body).isNotNull();
      if (body instanceof BookmarkInfo) {
        assertThat(((BookmarkInfo) body).getBookmarks()).isNotEmpty().extracting("description")
            .containsAll(expectedBookmarks.stream().map(Bookmark::getDescription)
                .collect(Collectors.toList()));
      } else if (body instanceof Bookmark) {
        assertThat(body).isNotNull().extracting("description")
            .isEqualTo(expectedBookmarks.get(0).getDescription());
      }
    });
  }
}


