package com.ninjaone.backendinterviewproject.exceptions.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "client.already-exist.error")
public class ClientAlreadyExistException extends RuntimeException {
}
