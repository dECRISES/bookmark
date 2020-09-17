package com.decrises.bms.bookmark.rest.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookmarkExceptionResponse {

  private String message;

  private String path;
}
