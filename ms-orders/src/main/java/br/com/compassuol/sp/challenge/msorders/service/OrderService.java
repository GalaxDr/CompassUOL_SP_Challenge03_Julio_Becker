package br.com.compassuol.sp.challenge.msorders.service;

import br.com.compassuol.sp.challenge.msorders.dtos.OrderDTO;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderResponse;
import br.com.compassuol.sp.challenge.msorders.entity.Order;

import javax.naming.ServiceUnavailableException;


public interface OrderService {
    void processOrder(OrderDTO orderDTO) throws ServiceUnavailableException, InterruptedException;
    OrderResponse getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir);
    OrderDTO createOrder(OrderDTO orderDTO);
    Order getOrderById(Long id);
    OrderDTO updateOrderStatus(Long id, String status);
}
