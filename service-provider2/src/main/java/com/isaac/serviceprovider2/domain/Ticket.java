package com.isaac.serviceprovider2.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket implements Serializable {
    private Integer ticketNumber;
    private String name;
}
