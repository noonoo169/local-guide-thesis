package com.example.localguidebe.crypto.rest.service;

import com.example.localguidebe.crypto.model.coinpayments.response.BaseResponse;
import com.example.localguidebe.crypto.model.coinpayments.response.DepositAddress;

public interface CoinpaymentsService {
    BaseResponse<DepositAddress> getDepositAddress(String currency);
    void getRates();
    BaseResponse<Object>getTransaction( double amount,  String currency1, String currency2, String buyer_email );
}
