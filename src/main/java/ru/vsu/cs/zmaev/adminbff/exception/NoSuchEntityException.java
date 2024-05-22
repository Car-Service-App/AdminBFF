package ru.vsu.cs.zmaev.adminbff.exception;

import org.springframework.http.HttpStatus;

public class NoSuchEntityException extends EntityException {
    private static final String MESSAGE = "No such entity %s with id: %s ";
    public NoSuchEntityException(Class<?> c, Long id) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE, c.getName(), id));
    }

    public NoSuchEntityException(Class<?> c, String name) {
        super(HttpStatus.NOT_FOUND, String.format(MESSAGE, c.getName(), name));
    }

    public NoSuchEntityException(String message) {
        super(HttpStatus.NOT_FOUND, String.format(message));
    }
}
