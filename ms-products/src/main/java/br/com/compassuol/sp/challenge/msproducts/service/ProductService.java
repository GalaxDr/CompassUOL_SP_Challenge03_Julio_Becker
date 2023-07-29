package br.com.compassuol.sp.challenge.msproducts.service;

import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
import br.com.compassuol.sp.challenge.msproducts.dtos.ProductResponse;


public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO);
    ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir);
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id,  ProductDTO productDTO);
    ProductDTO deleteProduct(Long id);
}
