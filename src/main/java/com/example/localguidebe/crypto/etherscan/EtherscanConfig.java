package com.example.localguidebe.crypto.etherscan;

public class EtherscanConfig {
  public static String recipientAddress = "0x80C7b8fcCA7bDF81Fb8993c1D5Ee992A87EBa470";
  public static String apiKey = "T59VK85DF6II181C2BXDUW7FDZIY8FNP81";
  public static String ethToUsdRateUrl =
      "https://api.etherscan.io/api?module=stats&action=ethprice";
  public static String transactionStatusUrl =
      "https://api-sepolia.etherscan.io/api?module=transaction&action=gettxreceiptstatus&txhash=&apikey=T59VK85DF6II181C2BXDUW7FDZIY8FNP81";

  public static String getTransactionStatusUrl(String txHash) {
    return String.format(
        "https://api-sepolia.etherscan.io/api?module=transaction&action=gettxreceiptstatus&txhash=%s&apikey=%s",
        txHash, apiKey);
  }
}
