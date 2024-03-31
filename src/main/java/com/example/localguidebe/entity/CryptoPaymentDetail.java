package com.example.localguidebe.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "crypto_payment_detail")
public class CryptoPaymentDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String tx;
  @Column private String sepoliaEthPrice;
  @Column private BigDecimal usdRate;
  @Column private String senderAddress;
  @Column private String recipientAddress;
}
