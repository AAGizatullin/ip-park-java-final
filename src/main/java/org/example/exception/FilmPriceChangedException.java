package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FilmPriceChangedException extends RuntimeException {
    public FilmPriceChangedException() {
    }

    public FilmPriceChangedException(String message) {
        super(message);
    }

    public FilmPriceChangedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FilmPriceChangedException(Throwable cause) {
        super(cause);
    }

    public FilmPriceChangedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
