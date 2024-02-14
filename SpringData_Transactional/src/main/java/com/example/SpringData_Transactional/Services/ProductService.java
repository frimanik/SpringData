package com.example.SpringData_Transactional.Services;

import com.example.SpringData_Transactional.Entities.Product;
import com.example.SpringData_Transactional.Exceptions.ProductNotFoundException;
import com.example.SpringData_Transactional.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product with this id does not exist"));
    }

    public Product updateProduct(Long id, Product product) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with this id does not exist");
        }
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product with this id does not exist");
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public List<Product> getAllOrderProducts(List<Long>productIds){
        return productRepository.findAllById(productIds);
    }
}
