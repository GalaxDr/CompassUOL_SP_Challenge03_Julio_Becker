package br.com.compassuol.sp.challenge.msorders.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO{
    private Long id;
    private Long userId;
    private List<Long> products;
    private String deliveryAddress;
    private String status;
    private String cep;
}
