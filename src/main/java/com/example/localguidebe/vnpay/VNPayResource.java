package com.example.localguidebe.vnpay;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment/")
public class VNPayResource {
  Logger logger = LoggerFactory.getLogger(VNPayResource.class);

  @Value("${frontend.host}")
  private String frontendHost;

  @Value("${backend.host}")
  private String backendHost;

  @GetMapping("payment-callback")
  public void paymentCallback(
      @RequestParam Map<String, String> queryParams, HttpServletResponse response)
      throws IOException {
    String vnp_ResponseCode = queryParams.get("vnp_ResponseCode");

    // TODO: change here to List bookingIds
    String contractId = queryParams.get("contractId");

    if (contractId != null && !contractId.equals("")) {
      if ("00".equals(vnp_ResponseCode)) {
        // TODO: Handle for success payment
        logger.info("Giao dịch thành công");
//         response.sendRedirect(frontendHost + "/info-student");
      } else {
        // Giao dịch thất bại
        // TODO: Handle for fail payment
        logger.error("Giao dịch thất bại");
        // response.sendRedirect(frontendHost + "/payment-failed");
      }
    }
  }

  @GetMapping("pay")
  public String getPay(@RequestParam("price") long price, @RequestParam("id") List<Long> bookingIds)
      throws UnsupportedEncodingException {

    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String orderType = "other";
    long amount = price * 100;
    String bankCode = "NCB";

    String vnp_TxnRef = Config.getRandomNumber(8);
    String vnp_IpAddr = "127.0.0.1";

    String vnp_TmnCode = Config.vnp_TmnCode;

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");

    //        vnp_Params.put("vnp_BankCode", bankCode);
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
    vnp_Params.put("vnp_OrderType", orderType);

    vnp_Params.put("vnp_Locale", "vn");

    String returnUrl = backendHost + Config.vnp_ReturnUrl + "?bookingId=" +
            bookingIds.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("&bookingId="));

    vnp_Params.put("vnp_ReturnUrl", returnUrl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (fieldValue.length() > 0)) {
        // Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        // Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

    return paymentUrl;
  }
}
