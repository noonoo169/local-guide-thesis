package com.example.localguidebe.crypto.etherscan;

import com.example.localguidebe.converter.InvoiceToInvoiceDtoConverter;
import com.example.localguidebe.dto.CryptoPayDto;
import com.example.localguidebe.entity.Invoice;
import com.example.localguidebe.service.InvoiceService;
import com.example.localguidebe.system.Result;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/etherscan")
public class EtherscanResource {
  Logger logger = LoggerFactory.getLogger(EtherscanResource.class);

  private final RestTemplate restTemplate;
  private final Gson gson;
  private final InvoiceService invoiceService;
  private final InvoiceToInvoiceDtoConverter invoiceToInvoiceDtoConverter;

  public EtherscanResource(
      RestTemplate restTemplate,
      Gson gson,
      InvoiceService invoiceService,
      InvoiceToInvoiceDtoConverter invoiceToInvoiceDtoConverter) {
    this.restTemplate = restTemplate;
    this.gson = gson;
    this.invoiceService = invoiceService;
    this.invoiceToInvoiceDtoConverter = invoiceToInvoiceDtoConverter;
  }

  @GetMapping("/get-eth-price")
  public ResponseEntity<Result> getEthPrice(@RequestParam() Double totalPrice) {
    String response = restTemplate.getForObject(EtherscanConfig.ethToUsdRateUrl, String.class);
    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
    JsonObject rates = jsonObject.getAsJsonObject("result");

    /*
    Suppose Seth = Eth, divide the real exchange rate by 100 to test multiple times because the
    number of seth coins is limited.
     */
    BigDecimal sethPrice =
        BigDecimal.valueOf(totalPrice)
            .divide(
                BigDecimal.valueOf(rates.get("ethusd").getAsDouble())
                    .multiply(BigDecimal.valueOf(100)),
                10,
                RoundingMode.HALF_UP);
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Result(
                true,
                HttpStatus.OK.value(),
                "Get payment url successfully",
                CryptoPayDto.builder()
                    .sepoliaEthPrice(sethPrice.toString())
                    .usdRate(BigDecimal.valueOf(rates.get("ethusd").getAsDouble()))
                    .build()));
  }

  @PostMapping("/make-invoice")
  public ResponseEntity<Result> makeInvoice(@RequestBody CryptoPayDto cryptoPayDto) {
    String getStatusUrl = EtherscanConfig.getTransactionStatusUrl(cryptoPayDto.txHash());
    String response = restTemplate.getForObject(getStatusUrl, String.class);
    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
    String message = jsonObject.get("message").getAsString();
    String status = jsonObject.get("status").getAsString();
    if (status.equals("0") || !message.equals("OK")) { // failed  transactions.
      logger.error("Giao dịch thất bại");
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(false, HttpStatus.CONFLICT.value(), "Create invoice failed"));
    }

    // successful transactions.
    Invoice invoice = invoiceService.createInvoiceByCrypto(cryptoPayDto);
    if (invoice == null) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Result(false, HttpStatus.CONFLICT.value(), "Create invoice failed"));
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            new Result(
                true, HttpStatus.OK.value(), "Create invoice successfully", invoice.getId()));
  }
}
