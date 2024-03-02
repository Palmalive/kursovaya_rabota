package com.example.kursovaya_rabota.order;

import com.example.kursovaya_rabota.appUser.AppUserService;
import com.example.kursovaya_rabota.basket.Basket;
import com.example.kursovaya_rabota.basket.BasketService;
import com.example.kursovaya_rabota.basket.basketItem.BasketItem;
import com.example.kursovaya_rabota.basket.basketItem.BasketItemService;
import com.example.kursovaya_rabota.menu.dish.DishService;
import com.example.kursovaya_rabota.order.orderItem.OrderItem;
import com.example.kursovaya_rabota.order.orderItem.OrderItemService;
import com.example.kursovaya_rabota.warehouse.warehouseItem.WarehouseItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final AppUserService appUserService;
    private final BasketService basketService;
    private final OrderRepository orderRepository;
    private final OrderItemService orderItemService;
    private final WarehouseItemService warehouseItemService;
    private final DishService dishService;
    private final BasketItemService basketItemService;

    public String createOrder(Principal principal) {
        Long appUserId = appUserService.getUserByPrincipal(principal).getId();
        Basket basket = basketService.getBasketByUserId(appUserId);
        if (basket.getBasketItems().isEmpty()){
            return "корзина пуста";
        }

        for (BasketItem item: basket.getBasketItems()){
            if (!dishService.isDishAvailable(item.getDish().getId())){
                basket.deleteBasketItem(item);
                basketService.saveBasket(basket);
                String dishName = item.getDish().getName();
                item.nullReferences();
                basketItemService.delete(item);
                return dishName+" недоступен";
            }
        }

        List<OrderItem> orderItems = basket.getBasketItems().stream()
                .map(basketItem -> new OrderItem(basketItem.getDish(), basketItem.getCount())).toList();
        orderItems.forEach(orderItemService::saveOrderItem);

        orderItems.forEach(item -> item.getDish().getIngredients()
                .forEach(ingredient -> warehouseItemService.decreaseIngredients(ingredient.getName(), ingredient.getWeight())));


        Orders order = new Orders(LocalDateTime.now(), OrderStatus.COOKING, orderItems);
        orderRepository.save(order);
        basketService.clearBasket(basket.getId());
        log.info("Order with id {} was created", order.getId());
        log.info("Basket with id {} was cleared", basket.getId());

        return null;
    }

    public List<Orders> getOrdersByStatus(OrderStatus orderStatus){
        return orderRepository.findAllByStatus(orderStatus);
    }

    public void changeOrderStatus(Long id, OrderStatus orderStatus){
        Orders order = orderRepository.findById(id).orElse(null);
        //TODO сделать что-то
        if (order != null) {
            order.setStatus(orderStatus);
            orderRepository.save(order);
        }
    }

    public List<Orders> getMonthOrders() {
        return orderRepository.findAllByLocalDateTimeGreaterThan(LocalDateTime.now().minusDays(30));
    }
}
