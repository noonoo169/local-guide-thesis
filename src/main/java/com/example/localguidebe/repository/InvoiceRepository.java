package com.example.localguidebe.repository;

import com.example.localguidebe.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Integer> {
}
