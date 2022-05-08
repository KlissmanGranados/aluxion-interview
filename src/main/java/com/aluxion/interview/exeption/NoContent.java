package com.aluxion.interview.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when no content is found.
 * @author Klissman Granados
 */
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class NoContent extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public NoContent(final String message) {
        super(message);
    }
}
