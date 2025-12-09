package com.jm.hellopay.controller;

import com.jm.hellopay.model.dto.CheckoutRequest;
import com.jm.hellopay.service.CheckoutService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/session")
    public Map<String, String> session(@RequestBody CheckoutRequest request) throws StripeException {
        return checkoutService.createSession(request);
    }
}
