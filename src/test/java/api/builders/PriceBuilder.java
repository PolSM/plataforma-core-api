package api.builders;

import api.domain.model.Price;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PriceBuilder {
    private Integer productId;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private Integer priority;
    private BigDecimal price;
    private String currency;

    public static PriceBuilder aPrice() {
        return new PriceBuilder()
                .setProductId(35455)
                .setBrandId(1)
                .setStartDate(LocalDateTime.of(2021, 1, 1, 0, 0))
                .setEndDate(LocalDateTime.of(2021, 12, 31, 23, 59))
                .setPriceList(1)
                .setPriority(0)
                .setPrice(new BigDecimal("35.50"))
                .setCurrency("EUR");
    }

    public PriceBuilder setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public PriceBuilder setBrandId(Integer brandId) {
        this.brandId = brandId;
        return this;
    }

    public PriceBuilder setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public PriceBuilder setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public PriceBuilder setPriceList(Integer priceList) {
        this.priceList = priceList;
        return this;
    }

    public PriceBuilder setPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    public PriceBuilder setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public PriceBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public Price build() {
        return new Price(productId, brandId, startDate, endDate, priceList, priority, price, currency);
    }
}