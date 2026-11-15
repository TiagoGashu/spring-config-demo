package com.example.orders;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bound from the "orders.*" keys. A @ConfigurationProperties bean with setters is
 * re-bound automatically when a refresh event arrives (no @RefreshScope needed).
 */
@ConfigurationProperties(prefix = "orders")
public class OrdersProperties {

    private int maxItemsPerOrder;
    private String defaultCurrency;
    private Map<String, Boolean> featureFlags = new HashMap<>();
    private Payment payment = new Payment();
    private Notification notification = new Notification();

    public static class Payment {
        private int timeoutMs;
        private int retries;
        public int getTimeoutMs() { return timeoutMs; }
        public void setTimeoutMs(int timeoutMs) { this.timeoutMs = timeoutMs; }
        public int getRetries() { return retries; }
        public void setRetries(int retries) { this.retries = retries; }
    }

    public static class Notification {
        private String emailFrom;
        private String bannerMessage;
        public String getEmailFrom() { return emailFrom; }
        public void setEmailFrom(String emailFrom) { this.emailFrom = emailFrom; }
        public String getBannerMessage() { return bannerMessage; }
        public void setBannerMessage(String bannerMessage) { this.bannerMessage = bannerMessage; }
    }

    public int getMaxItemsPerOrder() { return maxItemsPerOrder; }
    public void setMaxItemsPerOrder(int v) { this.maxItemsPerOrder = v; }
    public String getDefaultCurrency() { return defaultCurrency; }
    public void setDefaultCurrency(String v) { this.defaultCurrency = v; }
    public Map<String, Boolean> getFeatureFlags() { return featureFlags; }
    public void setFeatureFlags(Map<String, Boolean> v) { this.featureFlags = v; }
    public Payment getPayment() { return payment; }
    public void setPayment(Payment v) { this.payment = v; }
    public Notification getNotification() { return notification; }
    public void setNotification(Notification v) { this.notification = v; }
}
