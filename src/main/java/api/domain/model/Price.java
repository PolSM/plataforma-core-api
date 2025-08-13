package api.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Price(
        Integer productId,
        Integer brandId,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priceList,
        Integer priority,
        BigDecimal price,
        String currency
) {
    public boolean hasHigherPriorityThan(Price other) {
        return this.priority > other.priority();
    }
}