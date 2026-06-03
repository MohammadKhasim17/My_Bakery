package com.mybakery.service;

/**
 * Custom runtime exception thrown when a product cannot be found by ID.
 * Controllers catch this and return a 404 Not Found response.
 */
public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
