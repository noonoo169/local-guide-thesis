package com.example.localguidebe.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProvinceResponseDTO {
  private String name;
  private Long bookedQuantity;
  private String imageLink;
}
