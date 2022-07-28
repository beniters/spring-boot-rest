package com.ninjaone.backendinterviewproject.exceptions.clientservicedevice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "client-service-device.not-exist.error")
public class ClientServiceDeviceNotExistException extends RuntimeException {
}
