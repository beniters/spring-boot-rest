package com.ninjaone.backendinterviewproject.exceptions.clientservicedevice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "client-service-device.already-exist.error")
public class ClientServiceDeviceAlreadyExistException extends RuntimeException {
}
