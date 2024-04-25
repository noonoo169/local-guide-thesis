package com.example.localguidebe.crypto.client;

import com.example.localguidebe.crypto.model.coinpayments.response.BaseResponse;
import com.example.localguidebe.crypto.model.coinpayments.response.DepositAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "coinpayments", url = "${coinpayments.url}")

public interface CoinpaymentsClient {

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    BaseResponse<DepositAddress> getDepositAddres(@RequestBody String requestBody);

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    BaseResponse<Object> getRates(@RequestBody String requestBody);
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    BaseResponse<Object> getTransaction(@RequestBody String requestBody);

}

