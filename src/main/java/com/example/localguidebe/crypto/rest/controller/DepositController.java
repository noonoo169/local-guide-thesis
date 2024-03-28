package com.example.localguidebe.crypto.rest.controller;


import com.example.localguidebe.crypto.model.Currency;
import com.example.localguidebe.crypto.model.coinpayments.response.BaseResponse;
import com.example.localguidebe.crypto.model.coinpayments.response.DepositAddress;
import com.example.localguidebe.crypto.model.dto.DepositAddressDTO;
import com.example.localguidebe.crypto.rest.service.CoinpaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1")
public class DepositController {

    private final CoinpaymentsService coinpaymentsService;

    @Autowired
    public DepositController(CoinpaymentsService coinpaymentsService) {
        this.coinpaymentsService = coinpaymentsService;
    }

    @GetMapping(path = "/deposit-address/{currency}")
    public DepositAddressDTO getDepositAddress(@PathVariable Currency currency) {

        BaseResponse<DepositAddress> depositAddress = this.coinpaymentsService.getDepositAddress(currency.name());
        System.out.println();

        return DepositAddressDTO.builder()
                .address(depositAddress.getResult().getAddress())
                .build();
    }
    @GetMapping("/test/{amount}/{currency1}/{currency2}/{buyer_email}")
    public void getRates(@PathVariable("amount") double amount,@PathVariable("currency1") String currency1,@PathVariable("currency2") String currency2,@PathVariable("buyer_email")String buyer_email ){
        BaseResponse<Object> depositAddress = this.coinpaymentsService.getTransaction(amount,currency1,currency2,buyer_email);
        System.out.println();

    }
}
