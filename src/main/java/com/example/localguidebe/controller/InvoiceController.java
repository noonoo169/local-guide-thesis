package com.example.localguidebe.controller;

import com.example.localguidebe.converter.InvoiceToInvoiceDtoConverter;
import com.example.localguidebe.entity.Invoice;
import com.example.localguidebe.service.InvoiceService;
import com.example.localguidebe.system.Result;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  private final InvoiceService invoiceService;
  private final InvoiceToInvoiceDtoConverter invoiceToInvoiceDtoConverter;

  public InvoiceController(
      InvoiceService invoiceService, InvoiceToInvoiceDtoConverter invoiceToInvoiceDtoConverter) {
    this.invoiceService = invoiceService;
    this.invoiceToInvoiceDtoConverter = invoiceToInvoiceDtoConverter;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Result> getInvoiceById(@PathVariable("id") Long id) {
    Optional<Invoice> invoice = invoiceService.findById(id);
    return invoice
        .map(
            value ->
                ResponseEntity.status(HttpStatus.OK)
                    .body(
                        new Result(
                            true,
                            HttpStatus.NO_CONTENT.value(),
                            "Get invoice successfully",
                            invoiceToInvoiceDtoConverter.convert(value))))
        .orElseGet(
            () ->
                ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new Result(true, HttpStatus.NO_CONTENT.value(), "Not found invoice")));
  }
}
