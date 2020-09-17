package com.decrises.bms.bookmark.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import com.decrises.bms.bookmark.domain.exception.BookmarkNotFoundException;

@RestControllerAdvice(basePackages = {"com.decrises.bms.bookmark"})
public class BookmarkExceptionHandler {

  @ExceptionHandler(value = BookmarkNotFoundException.class)
  public final ResponseEntity<BookmarkExceptionResponse> handleBookmarkNotFoundException(
      final Exception exception, final WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        BookmarkExceptionResponse.builder().message(exception.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }
}
