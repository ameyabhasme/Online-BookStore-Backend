package com.bridgelabz.bookstore.dto;

import lombok.Data;

@Data
public class AddressDTO {
   public String fullName;

   public Long mobileNumber;

   public String address;

   public String city;

   public String state;

   public String addressType;
}
