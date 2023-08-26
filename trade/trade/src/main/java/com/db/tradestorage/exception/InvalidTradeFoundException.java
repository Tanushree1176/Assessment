package com.db.tradestorage.exception;

public class InvalidTradeFoundException extends RuntimeException {

    private final String id;

    public InvalidTradeFoundException(final String id) {
        super("Invalid Trade: " + id);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
