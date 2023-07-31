package br.com.compassuol.sp.challenge.msproducts.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductIdsRequest {
    @NotEmpty
    private List<Long> ids;
}
