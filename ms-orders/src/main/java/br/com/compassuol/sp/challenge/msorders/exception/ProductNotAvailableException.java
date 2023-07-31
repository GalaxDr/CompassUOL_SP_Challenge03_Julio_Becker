package br.com.compassuol.sp.challenge.msorders.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product(s) not available")
public class ProductNotAvailableException extends RuntimeException {
    private final String message;

    public ProductNotAvailableException(String message) {
        this.message = message;
    }

    public ProductNotAvailableException(String productNotAvailable, List<Long> notAvailableProductIds) {
        this.message = String.format("%s %s : '%s'", productNotAvailable, "id", notAvailableProductIds);
    }

    @Override
    public String getMessage() {
        return message;
    }
}