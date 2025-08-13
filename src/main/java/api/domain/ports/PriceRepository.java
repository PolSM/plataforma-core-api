package api.domain.ports;

import api.domain.model.Price;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PriceRepository {
    Optional<Price> findApplicablePrice(Integer productId, Integer brandId, LocalDateTime date);
    void save(Price price);
}
