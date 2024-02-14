package com.example.SpringData_Transactional;

import com.example.SpringData_Transactional.DTO.OrderDTO;
import com.example.SpringData_Transactional.Entities.Customer;
import com.example.SpringData_Transactional.Entities.Order;
import com.example.SpringData_Transactional.Entities.Product;
import com.example.SpringData_Transactional.Exceptions.OrderNotFoundException;
import com.example.SpringData_Transactional.Repositories.OrderRepository;
import com.example.SpringData_Transactional.Services.CustomerService;
import com.example.SpringData_Transactional.Services.OrderService;
import com.example.SpringData_Transactional.Services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void placeOrder_ValidOrder_ShouldReturnOrder() {

        long customerId = 1L;
        long productId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(customerId);
        orderDTO.setProductIds(Collections.singletonList(productId));

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setBalance(100);

        Product product = new Product();
        product.setId(productId);
        product.setQuantity(10);
        product.setPrice(50);

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProductById(productId)).thenReturn(product);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        // Act
        Order result = orderService.placeOrder(orderDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getTotalAmount()).isEqualTo(product.getPrice());
        verify(customerService, times(1)).getCustomer(customerId);
        verify(productService, times(1)).getProductById(productId);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void placeOrder_RollBackCheck() {
        long customerId = 1L;
        long productId = 1L;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCustomerId(customerId);
        orderDTO.setProductIds(Collections.singletonList(productId));

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setBalance(40); // Assuming insufficient balance

        Product product = new Product();
        product.setId(productId);
        product.setPrice(50); // Assuming product price
        product.setQuantity(10); // Assuming product quantity in stock

        when(customerService.getCustomer(customerId)).thenReturn(customer);
        when(productService.getProductById(productId)).thenReturn(product);

        // Act & Assert
        assertThatThrownBy(() -> orderService.placeOrder(orderDTO))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Insufficient balance");


        // Verify
        verify(customerService, times(1)).getCustomer(customerId);
        verify(productService, times(1)).getProductById(productId);
        verify(orderRepository, never()).save(any(Order.class)); // Save was never called, therefore transaction rolled back
    }

    @Test
    void getOrderById_ValidId_ReturnsOrder() {
        // Arrange
        long orderId = 1L;
        Order expectedOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        // Act
        Order result = orderService.getOrderById(orderId);

        // Assert
        assertThat(result).isEqualTo(expectedOrder);
    }

    @Test
    void getOrderById_InvalidId_ThrowsOrderNotFoundException() {
        // Arrange
        long invalidId = 2L;
        when(orderRepository.findById(invalidId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> orderService.getOrderById(invalidId))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void updateOrder_ValidOrder_ReturnsUpdatedOrder() {
        // Arrange
        long orderId = 1L;
        Order existingOrder = new Order();
        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.save(any(Order.class))).thenReturn(existingOrder);

        // Act
        Order result = orderService.updateOrder(orderId, existingOrder);

        // Assert
        assertThat(result).isEqualTo(existingOrder);
    }

    @Test
    void updateOrder_OrderDoesNotExist_ThrowsOrderNotFoundException() {
        // Arrange
        long nonExistingOrderId = 2L;
        Order nonExistingOrder = new Order();
        when(orderRepository.existsById(nonExistingOrderId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> orderService.updateOrder(nonExistingOrderId, nonExistingOrder))
                .isInstanceOf(OrderNotFoundException.class);
    }

    @Test
    void deleteOrder_ValidId_DeletesOrder() {
        // Arrange
        long orderId = 1L;
        when(orderRepository.existsById(orderId)).thenReturn(true);

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void deleteOrder_OrderDoesNotExist_ThrowsOrderNotFoundException() {
        // Arrange
        long nonExistingOrderId = 2L;
        when(orderRepository.existsById(nonExistingOrderId)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> orderService.deleteOrder(nonExistingOrderId))
                .isInstanceOf(OrderNotFoundException.class);
    }
}
