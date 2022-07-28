package com.ninjaone.backendinterviewproject.exceptions.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "service.not-exist.error")
public class ServiceNotExistException extends RuntimeException {
}
