package com.eestienergia.fundraiser.rest.payment;

import lombok.Value;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
class CashPaymentResponseResource {
    BigDecimal change;
}
