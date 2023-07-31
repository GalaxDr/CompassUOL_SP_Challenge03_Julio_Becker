package br.com.compassuol.sp.challenge.msproducts.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private Long userId;
    private List<Long> products;
    private String cep;
}