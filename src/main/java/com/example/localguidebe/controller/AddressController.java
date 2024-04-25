package com.example.localguidebe.controller;

import com.example.localguidebe.service.DistrictService;
import com.example.localguidebe.service.ProvinceService;
import com.example.localguidebe.service.WardService;
import com.example.localguidebe.system.Result;
import com.example.localguidebe.utils.AddressUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/addresses")
public class AddressController {
  private DistrictService districtService;
  private WardService wardService;

  private ProvinceService provinceService;

  @Autowired
  public void AddressController(
      DistrictService districtService, WardService wardService, ProvinceService provinceService) {
    this.districtService = districtService;
    this.wardService = wardService;
    this.provinceService = provinceService;
  }

  @GetMapping("/provinces")
  public ResponseEntity<Result> getAddress() {

    try {

      List<String> provinces = new ArrayList<>();
      provinces.addAll(provinceService.getProvinceByName());

      return new ResponseEntity<>(
          new Result(false, HttpStatus.OK.value(), "Search for province successfully", provinces),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Province not found", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/districts")
  public ResponseEntity<Result> getDistricts(
      @RequestParam(required = false, defaultValue = "0") String provinceName) {

    try {
      provinceName = AddressUtils.removeVietnameseAccents(provinceName);
      List<String> districts = new ArrayList<>();
      districts.addAll(districtService.getDistrictByProvince(provinceName));

      return new ResponseEntity<>(
          new Result(false, HttpStatus.OK.value(), "Search district successfully", districts),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "No district found", null),
          HttpStatus.CONFLICT);
    }
  }

  @GetMapping("/wards")
  public ResponseEntity<Result> getWards(
      @RequestParam(required = false, defaultValue = "0") String districtName) {
    try {
      districtName = AddressUtils.removeVietnameseAccents(districtName);
      List<String> wards = new ArrayList<>();
      wards.addAll(wardService.getWardByDistrict(districtName));

      return new ResponseEntity<>(
          new Result(false, HttpStatus.OK.value(), "Search for wards successfully", wards),
          HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(
          new Result(false, HttpStatus.CONFLICT.value(), "Ward not found", null),
          HttpStatus.CONFLICT);
    }
  }
}
