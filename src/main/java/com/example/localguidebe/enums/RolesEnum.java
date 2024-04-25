package com.example.localguidebe.enums;

import lombok.Getter;

@Getter
public enum RolesEnum {
  GUIDER(1),
  TRAVELER(2),
  ADMIN(3);

  private final int value;

  RolesEnum(int value) {
    this.value = value;
  }
}
