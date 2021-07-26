package com.isaac.serviceconsumer1.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Ticket implements Serializable {
    private Integer ticketNumber;
    private String name;

}
