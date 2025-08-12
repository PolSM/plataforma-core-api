package api.application.dtos;

import java.time.LocalDateTime;
import java.util.Objects;

public class PriceDTO {
    private Integer productId;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Float price;
    private String currency;

    public PriceDTO() {
    }

    public PriceDTO(Integer productId, Integer brandId, LocalDateTime startDate, LocalDateTime endDate, Float price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceDTO priceDTO = (PriceDTO) o;
        return Objects.equals(productId, priceDTO.productId) && Objects.equals(brandId, priceDTO.brandId) && Objects.equals(startDate, priceDTO.startDate) && Objects.equals(endDate, priceDTO.endDate) && Objects.equals(price, priceDTO.price) && Objects.equals(currency, priceDTO.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, brandId, startDate, endDate, price, currency);
    }
}