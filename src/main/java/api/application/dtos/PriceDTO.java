package api.application.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode
public class PriceDTO {
    private Integer productId;
    private Integer brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;

    public PriceDTO() {
    }

    public PriceDTO(Integer productId, Integer brandId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, String currency) {
        this.productId = productId;
        this.brandId = brandId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.currency = currency;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}