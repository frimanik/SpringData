package com.example.SpringData_Transactional.Services;

import com.example.SpringData_Transactional.DTO.OrderDTO;
import com.example.SpringData_Transactional.Entities.Customer;
import com.example.SpringData_Transactional.Entities.Order;
import com.example.SpringData_Transactional.Entities.Product;
import com.example.SpringData_Transactional.Exceptions.OrderNotFoundException;
import com.example.SpringData_Transactional.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class OrderService {

    private final CustomerService customerService;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(CustomerService customerService, OrderRepository orderRepository, ProductService productService) {
        this.customerService = customerService;
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Transactional
    public Order placeOrder(OrderDTO orderDto) {
        Customer customer = customerService.getCustomer(orderDto.getCustomerId());
        long totalAmount = 0;
        for (Long productId : orderDto.getProductIds()) {
            Product product = productService.getProductById(productId);
            totalAmount += product.getPrice();
            if (product.getQuantity() <= 0) {
                throw new IllegalStateException("Product out of stock");
            }
            product.setQuantity(product.getQuantity() - 1);
        }
        if (customer.getBalance() < totalAmount) {
            throw new IllegalStateException("Insufficient balance");
        }
        customer.setBalance(customer.getBalance() - totalAmount);

        Order order = new Order();
        order.setCustomer(customer);
        List<Product> products = productService.getAllOrderProducts(orderDto.getProductIds());
        order.setProducts(products);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with this is does not exist"));
    }

    public Order updateOrder(Long id, Order order) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with this is does not exist");
        }
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order with this is does not exist");
        }
        orderRepository.deleteById(id);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFoundException(OrderNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }
}
