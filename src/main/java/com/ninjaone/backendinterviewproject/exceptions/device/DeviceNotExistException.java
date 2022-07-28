package com.ninjaone.backendinterviewproject.exceptions.device;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "device.not-exist.error")
public class DeviceNotExistException extends RuntimeException {
}
