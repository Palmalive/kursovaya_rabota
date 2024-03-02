package com.example.kursovaya_rabota.analytics;

import com.example.kursovaya_rabota.menu.dish.Dish;
import com.example.kursovaya_rabota.order.OrderService;
import com.example.kursovaya_rabota.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final OrderService orderService;

    public Map<LocalDate, Double> getMonthReportSumPerDay(){
        return orderService.getMonthOrders().stream()
                .collect(Collectors.groupingBy(order -> order.getLocalDateTime().toLocalDate()
                        , Collectors.summingDouble(Orders::getPrice)));

    }

    public Map<Dish, Integer> getMonthReportCountDish() {
        return  orderService.getMonthOrders().stream()
                .flatMap(order -> order.getOrderItems().stream())
                .collect(HashMap::new, (map, orderItem) -> map.merge(orderItem.getDish(), orderItem.getCount(), Integer::sum), HashMap::putAll);
    }

    public Map<Integer, Integer> getMonthReportCountPerHour(){
        return orderService.getMonthOrders().stream()
                .collect(Collectors.groupingBy(
                        orders -> orders.getLocalDateTime().getHour(),
                        Collectors.summingInt(orders -> 1)
                ));
    }
}
