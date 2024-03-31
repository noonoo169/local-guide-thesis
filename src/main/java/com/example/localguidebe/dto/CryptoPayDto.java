package com.example.localguidebe.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record CryptoPayDto(
    Double priceTotal, // usd
    String txHash,
    String sepoliaEthPrice,
    BigDecimal usdRate,
    String senderAddress,
    List<Long> bookingIds,
    String travelerEmail,
    String email,
    String fullName,
    String phone) {}
