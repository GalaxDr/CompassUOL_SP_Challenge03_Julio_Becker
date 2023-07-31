package br.com.compassuol.sp.challenge.msorders.service.impl;

import br.com.compassuol.sp.challenge.msorders.dtos.AddressResponse;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderDTO;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderResponse;
import br.com.compassuol.sp.challenge.msorders.dtos.OrderResponseDTO;
import br.com.compassuol.sp.challenge.msorders.entity.Order;
import br.com.compassuol.sp.challenge.msorders.exception.ResourceNotFoundException;
import br.com.compassuol.sp.challenge.msorders.fein.ViaCepClient;
import br.com.compassuol.sp.challenge.msorders.rabbitmq.ProductRequestProducer;
import br.com.compassuol.sp.challenge.msorders.repository.OrderRepository;
import br.com.compassuol.sp.challenge.msorders.service.OrderService;
import br.com.compassuol.sp.challenge.msorders.utils.AppConstants;
import br.com.compassuol.sp.challenge.msproducts.dtos.ProductDTO;
import br.com.compassuol.sp.challenge.msproducts.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRequestProducer productRequestProducer;
    private final ViaCepClient viaCepClient;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, ProductRequestProducer productMessageProducer, ViaCepClient viaCepClient) {
        this.viaCepClient = viaCepClient;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.productRequestProducer = productMessageProducer;
    }

    @Override
    public void processOrder(OrderDTO orderDTO) {
        orderDTO.setStatus(AppConstants.OrderStatus.PENDING.name());
        AddressResponse addressResponse = viaCepClient.getAddressByCep(orderDTO.getCep());
        orderDTO.setDeliveryAddress(addressResponse.getLogradouro() + ", " + addressResponse.getBairro() + ", " + addressResponse.getLocalidade() + ", " + addressResponse.getUf());

        List<Long> productIds = orderDTO.getProducts();

        Order order = new Order();
        order.setStatus(AppConstants.OrderStatus.PENDING.name());
        order.setDeliveryAddress(orderDTO.getDeliveryAddress());
        order.setUserId(orderDTO.getUserId());
        order.setProductIds(productIds);

        orderRepository.save(order);
    }
    @Override
    public OrderResponse getAllOrders(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Order> orders = orderRepository.findAll(pageable);
        List<Order> orderList = orders.getContent();
        List<OrderDTO> content = orderList.stream().map(this::mapToDto).toList();
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setContent(content);
        orderResponse.setPageNo(orders.getNumber());
        orderResponse.setPageSize(orders.getSize());
        orderResponse.setTotalElements(orders.getTotalElements());
        orderResponse.setTotalPages(orders.getTotalPages());
        orderResponse.setLast(orders.isLast());
        return orderResponse;
    }
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        AddressResponse deliveryAddressDTO = viaCepClient.getAddressByCep(orderDTO.getCep());
        orderDTO.setDeliveryAddress(buildAddressString(deliveryAddressDTO));
        orderDTO.setStatus(AppConstants.OrderStatus.PENDING.name());
        return orderDTO;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found", "id", id));
    }

    @Override
    public OrderDTO updateOrderStatus(Long id, String status) {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(status);
            status = jsonNode.get("status").asText();
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Formato de JSON inválido: " + status);
        }

        AppConstants.OrderStatus orderStatus;
        try {
            orderStatus = AppConstants.OrderStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
        Order order = getOrderById(id);
        order.setStatus(String.valueOf(orderStatus));
        Order updatedOrder = orderRepository.save(order);
        return mapToDto(updatedOrder);
    }
    private OrderDTO mapToDto(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setStatus(order.getStatus());
        dto.setProducts(order.getProductIds());
        return dto;
    }

    private Order mapToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
    private String buildAddressString(AddressResponse addressResponse) {
        return addressResponse.getLogradouro() + ", " +
                addressResponse.getBairro() + ", " +
                addressResponse.getLocalidade() + ", " +
                addressResponse.getUf() + ", " +
                addressResponse.getCep();
    }
    private Product mapToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
    private OrderResponseDTO mapToResponseDto(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, OrderResponseDTO.class);
    }
}
