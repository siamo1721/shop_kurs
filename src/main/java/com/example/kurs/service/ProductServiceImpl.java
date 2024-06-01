package com.example.kurs.service;

import com.example.kurs.exception.InsufficientStockException;
import com.example.kurs.exception.ResourceNotFoundException;
import com.example.kurs.model.Product;
import com.example.kurs.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new ResourceNotFoundException("Product not found with id " + id);
                });
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Creating new product with details: {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        log.info("Updating product with id: {}", id);
        Product product = getProductById(id);
        product.setName(productDetails.getName());
        product.setQuantity(productDetails.getQuantity());
        product.setPrice(productDetails.getPrice());
        product.setDiscount(productDetails.getDiscount());
        log.info("Product with id: {} updated with new details: {}", id, productDetails);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        Product product = getProductById(id);
        productRepository.delete(product);
        log.info("Product with id: {} deleted", id);
    }

    @Override
    public void reduceProductQuantity(Long productId, int quantity) {
        log.info("Reducing quantity of product with id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", productId);
                    return new ResourceNotFoundException("Product not found");
                });

        if (product.getQuantity() < quantity) {
            log.error("Insufficient stock for product: {}", product.getName());
            throw new InsufficientStockException("Insufficient stock for product: " + product.getName());
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Product quantity reduced for product with id: {}", productId);
    }
}
