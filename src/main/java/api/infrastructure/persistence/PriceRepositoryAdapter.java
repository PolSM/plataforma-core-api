package api.infrastructure.persistence;

import api.domain.model.Price;
import api.domain.ports.PriceRepository;
import api.infrastructure.persistence.entities.PriceJpaEntity;
import api.infrastructure.persistence.mappers.PriceJpaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public class PriceRepositoryAdapter implements PriceRepository {

    private final BaseJpaPriceRepository jpaPriceRepository;
    private final PriceJpaMapper mapper;

    public PriceRepositoryAdapter(
            BaseJpaPriceRepository jpaPriceRepository,
            PriceJpaMapper mapper
    ) {
        this.jpaPriceRepository = jpaPriceRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Price> findApplicablePrice(Integer productId, Integer brandId, LocalDateTime date) {
        return jpaPriceRepository.findPricesByProductIdAndBrandIdAndDate(
                productId,
                brandId,
                date
        ).map(mapper::toDomain);
    }

    @Override
    public void save(Price price) {
        PriceJpaEntity entity = mapper.toEntity(price);
        jpaPriceRepository.save(entity);
    }
}