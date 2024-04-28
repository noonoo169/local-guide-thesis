package com.example.localguidebe.service.impl;

import com.example.localguidebe.repository.ProvinceRepository;
import com.example.localguidebe.service.ProvinceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProvinceServiceImpl implements ProvinceService {
  private final ProvinceRepository provinceRepository;

  @Autowired
  public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
    this.provinceRepository = provinceRepository;
  }

  public List<String> getProvinceByName() {
    return provinceRepository.getProvinceByName();
  }
}
