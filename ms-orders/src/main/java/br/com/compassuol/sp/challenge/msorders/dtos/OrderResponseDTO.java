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
public class OrderResponseDTO {
    private Long userId;
    private List<Long> products;
    private String deliveryAddress;
    private String status;

    public OrderResponseDTO(String message) {
        this.status = message;
    }
}
