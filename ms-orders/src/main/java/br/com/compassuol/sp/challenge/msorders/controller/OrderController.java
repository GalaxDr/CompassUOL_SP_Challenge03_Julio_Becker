package br.com.compassuol.sp.challenge.msorders.controller;

import br.com.compassuol.sp.challenge.msorders.dtos.OrderDTO;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderResponse;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderResponseDTO;
import br.com.compassuol.sp.challenge.msorders.entity.Order;
import br.com.compassuol.sp.challenge.msorders.exception.ProductNotAvailableException;
import br.com.compassuol.sp.challenge.msorders.rabbitmq.ProductRequestProducer;
import br.com.compassuol.sp.challenge.msorders.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.ServiceUnavailableException;

import static br.com.compassuol.sp.challenge.msorders.utils.AppConstants.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;


    public OrderController(OrderService orderService, ModelMapper modelMapper, ProductRequestProducer productMessageProducer) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            OrderDTO createdOrder = orderService.createOrder(orderDTO);
            orderService.processOrder(createdOrder);
            return new ResponseEntity<>(mapToOrderResponseDTO(createdOrder), HttpStatus.CREATED);
        } catch (ProductNotAvailableException e) {
            return new ResponseEntity<>(new OrderResponseDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (ServiceUnavailableException e) {
            return new ResponseEntity<>(new OrderResponseDTO(e.getMessage()), HttpStatus.SERVICE_UNAVAILABLE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private OrderResponseDTO mapToOrderResponseDTO(OrderDTO createdOrder) {
        return modelMapper.map(createdOrder, OrderResponseDTO.class);
    }

    @GetMapping
    public OrderResponse getAllOrders(@RequestParam(value = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = DEFAULT_SORT_BY) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = DEFAULT_SORT_DIR) String sortDir) {
        return orderService.getAllOrders(pageNo, pageSize, sortBy, sortDir);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        OrderDTO updatedOrder = orderService.updateOrderStatus(id, status);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}
