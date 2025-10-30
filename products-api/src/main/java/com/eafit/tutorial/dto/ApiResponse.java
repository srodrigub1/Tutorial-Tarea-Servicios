package com.eafit.tutorial.dto;

public record ApiResponse<T>(boolean ok, String message, T data) {}
