package com.aluxion.interview.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BadRequestException class. Throws when the request is bad.
 *
 * @author Klissman Granados
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public BadRequest(final String message) {
        super(message);
    }
}
