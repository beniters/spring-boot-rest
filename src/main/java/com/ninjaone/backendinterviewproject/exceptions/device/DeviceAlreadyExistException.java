package com.ninjaone.backendinterviewproject.exceptions.device;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "device.already-exist.error")
public class DeviceAlreadyExistException extends RuntimeException {
}
