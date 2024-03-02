package com.example.kursovaya_rabota.order.orderItem;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public void saveOrderItem(OrderItem orderItem){
        orderItemRepository.save(orderItem);
        log.info("Order Item with id {} was saved", orderItem.getId());
    }
}
