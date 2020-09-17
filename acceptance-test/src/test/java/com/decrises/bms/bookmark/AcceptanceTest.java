package com.decrises.bms.bookmark;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import com.decrises.bms.bookmark.domain.BookmarkDomain;
import com.decrises.bms.bookmark.domain.exception.BookmarkNotFoundException;
import com.decrises.bms.bookmark.domain.model.Bookmark;
import com.decrises.bms.bookmark.domain.model.BookmarkInfo;
import com.decrises.bms.bookmark.domain.port.ObtainBookmark;
import com.decrises.bms.bookmark.domain.port.RequestBookmark;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get bookmarks when asked for bookmarks from hard coded bookmarks")
  public void getBookmarksFromHardCoded() {
  /*
      RequestBookmark    - left side port
      BookmarkDomain     - hexagon (domain)
      ObtainBookmark     - right side port
   */
    RequestBookmark requestBookmark = new BookmarkDomain(); // the bookmark is hard coded
    BookmarkInfo bookmarkInfo = requestBookmark.getBookmarks();
    assertThat(bookmarkInfo).isNotNull();
    assertThat(bookmarkInfo.getBookmarks()).isNotEmpty().extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get bookmarks when asked for bookmarks from stub")
  public void getBookmarksFromMockedStub(@Mock ObtainBookmark obtainBookmark) {
    // Stub
    Bookmark bookmark = Bookmark.builder().code(1L).description(
        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
        .build();
    Mockito.lenient().when(obtainBookmark.getAllBookmarks()).thenReturn(List.of(bookmark));
    // hexagon
    RequestBookmark requestBookmark = new BookmarkDomain(obtainBookmark);
    BookmarkInfo bookmarkInfo = requestBookmark.getBookmarks();
    assertThat(bookmarkInfo).isNotNull();
    assertThat(bookmarkInfo.getBookmarks()).isNotEmpty().extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get bookmark when asked for bookmark by id from stub")
  public void getBookmarkByIdFromMockedStub(@Mock ObtainBookmark obtainBookmark) {
    // Given
    // Stub
    Long code = 1L;
    String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    Bookmark expectedBookmark = Bookmark.builder().code(code).description(description).build();
    Mockito.lenient().when(obtainBookmark.getBookmarkByCode(code))
        .thenReturn(Optional.of(expectedBookmark));
    // When
    RequestBookmark requestBookmark = new BookmarkDomain(obtainBookmark);
    Bookmark actualBookmark = requestBookmark.getBookmarkByCode(code);
    assertThat(actualBookmark).isNotNull().isEqualTo(expectedBookmark);
  }

  @Test
  @DisplayName("should throw exception when asked for bookmark by id that does not exists from stub")
  public void getExceptionWhenAskedBookmarkByIdThatDoesNotExist(@Mock ObtainBookmark obtainBookmark) {
    // Given
    // Stub
    Long code = -1000L;
    Mockito.lenient().when(obtainBookmark.getBookmarkByCode(code)).thenReturn(Optional.empty());
    // When
    RequestBookmark requestBookmark = new BookmarkDomain(obtainBookmark);
    // Then
    assertThatThrownBy(() -> requestBookmark.getBookmarkByCode(code)).isInstanceOf(
        BookmarkNotFoundException.class)
        .hasMessageContaining("Bookmark with code " + code + " does not exist");
  }
}
