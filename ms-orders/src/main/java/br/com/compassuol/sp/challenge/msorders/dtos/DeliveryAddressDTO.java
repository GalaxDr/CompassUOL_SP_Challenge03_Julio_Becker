package br.com.compassuol.sp.challenge.msorders.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAddressDTO {

    private String zipCode;

    private String street;

    private String complement;

    private String district;

    private String city;

    private String state;
}
