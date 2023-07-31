package br.com.compassuol.sp.challenge.msorders.fein;

import br.com.compassuol.sp.challenge.msproducts.dtos.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name = "ms-products", url = "http://localhost:8082")
public interface ProductFeignClient {

    @GetMapping("/products")
    ResponseEntity<ProductResponse> getAllProducts();
}

