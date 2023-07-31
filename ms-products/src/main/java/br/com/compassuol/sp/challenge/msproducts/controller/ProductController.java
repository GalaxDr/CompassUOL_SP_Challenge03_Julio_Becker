package br.com.compassuol.sp.challenge.msproducts.controller;

import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
import br.com.compassuol.sp.challenge.msproducts.dtos.ProductResponse;
import br.com.compassuol.sp.challenge.msproducts.rabbitmq.OrderRequestConsumer;
import br.com.compassuol.sp.challenge.msproducts.service.ProductService;
import br.com.compassuol.sp.challenge.msproducts.utils.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final OrderRequestConsumer productMessageConsumer;

    public ProductController(ProductService productService, OrderRequestConsumer productMessageConsumer) {
        this.productService = productService;
        this.productMessageConsumer = productMessageConsumer;
    }
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productService.createProduct(productDTO), HttpStatus.CREATED);
    }
    @GetMapping
    public ProductResponse getAllProducts(@RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int pageNo,
                                          @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int pageSize,
                                          @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY) String sortBy,
                                          @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR) String sortDir){
        return productService.getAllProducts(pageNo, pageSize, sortBy, sortDir);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @PathVariable Long id, @RequestBody ProductDTO productDTO){
        ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        ProductDTO deletedProduct = productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successful", HttpStatus.OK);
    }
}
