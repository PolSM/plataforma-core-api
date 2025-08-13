package api.application.services;

import api.application.dtos.PriceDTO;
import api.domain.model.Price;
import api.domain.ports.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PriceApplicationService {

    private final PriceRepository priceRepository;

    @Autowired
    public PriceApplicationService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<PriceDTO> getPrice(Integer productId, Integer brandId, LocalDateTime date) {
        Optional<Price> price = priceRepository.findApplicablePrice(productId, brandId, date);
        return price.map(p -> new PriceDTO(
                p.productId(),
                p.brandId(),
                p.startDate(),
                p.endDate(),
                p.price(),
                p.currency()
        ));
    }
}