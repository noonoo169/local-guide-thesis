package com.example.localguidebe.utils;

import com.example.localguidebe.system.Result;
import java.util.function.Supplier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public class AuthUtils {
  public static ResponseEntity<Result> checkAuthentication(
      Authentication authentication, Supplier<ResponseEntity<Result>> responseSupplier) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(new Result(false, HttpStatus.UNAUTHORIZED.value(), "Let's login"));
    }
    return responseSupplier.get(); // Execute the supplied logic if authentication is successful
  }
}
