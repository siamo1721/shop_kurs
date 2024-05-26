package com.example.kurs.service;

import com.example.kurs.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);

    void reduceProductQuantity(Long productId, int quantity);
}
