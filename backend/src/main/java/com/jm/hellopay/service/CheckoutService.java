package com.jm.hellopay.service;

import com.jm.hellopay.configuration.StripeProperties;
import com.jm.hellopay.model.dto.CheckoutRequest;
import com.jm.hellopay.repository.ProductRepository;
import com.jm.hellopay.utils.F;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final ProductRepository productRepository;
    private final StripeProperties stripeProperties;

    public Map<String, String> createSession(CheckoutRequest request) throws StripeException {

        final var productResult = productRepository.findById(request.productId());

        if(productResult.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        final var product = productResult.get();

        final var priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount(F.toLong(product.getPrice()))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(product.getName())
                                .addImage(product.getImageUrl())
                                .setDescription(product.getDescription())
                                .build())
                .build();

        final var params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(stripeProperties.webhooks().success())
                .setCancelUrl(stripeProperties.webhooks().cancel())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity((long) request.quantity())
                                .setPriceData(priceData)
                                .build())
                .build();

        final var session = Session.create(params);

        final var response = new HashMap<String, String>();
        response.put("id", session.getId());

        return response;
    }
}
