package br.com.compassuol.sp.challenge.msorders.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitProductDTO implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
}