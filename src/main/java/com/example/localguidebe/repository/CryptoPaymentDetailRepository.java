package com.example.localguidebe.repository;

import com.example.localguidebe.entity.CryptoPaymentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoPaymentDetailRepository extends JpaRepository<CryptoPaymentDetail, Long> {}
