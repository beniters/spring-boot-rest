package com.ninjaone.backendinterviewproject.exceptions.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "client.not-exist.error")
public class ClientNotExistException extends RuntimeException {
}
