package com.decrises.bms.bookmark.domain.port;

import java.util.List;
import java.util.Optional;
import com.decrises.bms.bookmark.domain.model.Bookmark;

public interface ObtainBookmark {

  default List<Bookmark> getAllBookmarks() {
    Bookmark bookmark = Bookmark.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build();
    return List.of(bookmark);
  }

  default Optional<Bookmark> getBookmarkByCode(Long code) {
    return Optional.of(Bookmark.builder().code(1L).description(
        "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)")
        .build());
  }
}
