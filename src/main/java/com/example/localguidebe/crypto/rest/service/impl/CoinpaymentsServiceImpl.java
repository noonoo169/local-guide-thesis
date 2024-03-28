package com.example.localguidebe.crypto.rest.service.impl;

import com.example.localguidebe.crypto.client.CoinpaymentsClient;
import com.example.localguidebe.crypto.configuration.CoinpaymentsProperties;
import com.example.localguidebe.crypto.model.Cmd;
import com.example.localguidebe.crypto.model.coinpayments.response.BaseResponse;
import com.example.localguidebe.crypto.model.coinpayments.response.DepositAddress;
import com.example.localguidebe.crypto.model.coinpayments.resquest.Paybody;
import com.example.localguidebe.crypto.model.coinpayments.resquest.RequestBody;
import com.example.localguidebe.crypto.rest.service.CoinpaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinpaymentsServiceImpl implements CoinpaymentsService {

    private final CoinpaymentsProperties coinpaymentsProperties;
    private final CoinpaymentsClient coinpaymentsClient;

    @Autowired
    public CoinpaymentsServiceImpl(CoinpaymentsProperties coinpaymentsProperties,
                                   CoinpaymentsClient coinpaymentsClient) {
        this.coinpaymentsProperties = coinpaymentsProperties;
        this.coinpaymentsClient = coinpaymentsClient;
    }

    @Override
    public BaseResponse<DepositAddress> getDepositAddress(String currency) {
        final String requestBody = new RequestBody(coinpaymentsProperties.getVersion(), Cmd.rates.getCmd(),
                currency, coinpaymentsProperties.getPublicKey()).toString();
            BaseResponse<Object> objectBaseResponse =  coinpaymentsClient.getRates(requestBody);

        System.out.println(1);
        return coinpaymentsClient.getDepositAddres(requestBody);
    }

    @Override
    public void getRates() {

    }

    @Override
    public BaseResponse getTransaction(double amount, String currency1, String currency2, String buyer_email) {
        final String re= new Paybody(coinpaymentsProperties.getVersion(),amount,Cmd.create_transaction.getCmd(),currency1,currency2,buyer_email,coinpaymentsProperties.getPublicKey()).toString();
        BaseResponse<Object> objectBaseResponse =  coinpaymentsClient.getTransaction(re);
        System.out.println(1);
        return null ;
    }


}
