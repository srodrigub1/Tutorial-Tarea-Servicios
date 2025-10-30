package com.eafit.tutorial.exception;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String name) {
        super("Product already exists: " + name);
    }
}
