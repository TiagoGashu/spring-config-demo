package com.example.orders;

import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** @RefreshScope recreates this bean (and its @Value fields) on every refresh event. */
@RestController
@RefreshScope
public class ConfigController {

    private final OrdersProperties props;

    @Value("${company.name}")
    private String companyName;

    @Value("${orders.notification.banner-message}")
    private String banner;

    public ConfigController(OrdersProperties props) {
        this.props = props;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {
        return Map.of(
            "company", companyName,
            "banner", banner,
            "maxItemsPerOrder", props.getMaxItemsPerOrder(),
            "currency", props.getDefaultCurrency(),
            "featureFlags", props.getFeatureFlags(),
            "paymentTimeoutMs", props.getPayment().getTimeoutMs(),
            "paymentRetries", props.getPayment().getRetries());
    }
}
