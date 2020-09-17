package com.decrises.bms.bookmark.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class BookmarkJpaTest {

  @Autowired
  private ObtainBookmark obtainBookmark;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given bookmarks exist in database when asked should return all bookmarks from database")
  public void shouldGiveMeBookmarksWhenAskedGivenBookmarkExistsInDatabase() {
    // Given from @Sql
    // When
    List<Bookmark> bookmarks = obtainBookmark.getAllBookmarks();
    // Then
    assertThat(bookmarks).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no bookmarks exists in database when asked should return empty")
  public void shouldGiveNoBookmarkWhenAskedGivenBookmarksDoNotExistInDatabase() {
    // When
    List<Bookmark> bookmarks = obtainBookmark.getAllBookmarks();
    // Then
    assertThat(bookmarks).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given bookmarks exists in database when asked for bookmark by id should return the bookmark")
  public void shouldGiveTheBookmarkWhenAskedByIdGivenThatBookmarkByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    Optional<Bookmark> bookmark = obtainBookmark.getBookmarkByCode(1L);
    // Then
    assertThat(bookmark).isNotNull().isNotEmpty().get().isEqualTo(Bookmark.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given bookmarks exists in database when asked for bookmark by id that does not exist should give empty")
  public void shouldGiveNoBookmarkWhenAskedByIdGivenThatBookmarkByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    Optional<Bookmark> bookmark = obtainBookmark.getBookmarkByCode(-1000L);
    // Then
    assertThat(bookmark).isEmpty();
  }
}
