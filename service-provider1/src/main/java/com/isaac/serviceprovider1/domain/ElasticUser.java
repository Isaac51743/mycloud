package com.isaac.serviceprovider1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ElasticUser {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private Double height;
}
