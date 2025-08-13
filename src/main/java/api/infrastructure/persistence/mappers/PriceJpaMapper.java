package api.infrastructure.persistence.mappers;

import api.domain.model.Price;
import api.infrastructure.persistence.entities.PriceJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class PriceJpaMapper {

    public Price toDomain(PriceJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Price(
                entity.getProductId(),
                entity.getBrandId(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriceList(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurrency()
        );
    }

    public PriceJpaEntity toEntity(Price domainModel) {
        if (domainModel == null) {
            return null;
        }
        PriceJpaEntity entity = new PriceJpaEntity();
        entity.setProductId(domainModel.productId());
        entity.setBrandId(domainModel.brandId());
        entity.setStartDate(domainModel.startDate());
        entity.setEndDate(domainModel.endDate());
        entity.setPriceList(domainModel.priceList());
        entity.setPriority(domainModel.priority());
        entity.setPrice(domainModel.price());
        entity.setCurrency(domainModel.currency());
        return entity;
    }
}