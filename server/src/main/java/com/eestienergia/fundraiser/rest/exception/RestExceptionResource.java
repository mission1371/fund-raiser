package com.eestienergia.fundraiser.rest.exception;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class RestExceptionResource {
    String message;
}
