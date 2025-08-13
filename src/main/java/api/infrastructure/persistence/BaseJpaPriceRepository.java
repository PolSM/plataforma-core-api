package api.infrastructure.persistence;

import api.infrastructure.persistence.entities.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BaseJpaPriceRepository extends JpaRepository<PriceJpaEntity, Long> {
    @Query("""
                SELECT p FROM PriceJpaEntity p
                WHERE p.productId = :productId
                AND p.brandId = :brandId
                AND :applicationDate BETWEEN p.startDate AND p.endDate
                ORDER BY p.priority DESC
                LIMIT 1
            """)
    Optional<PriceJpaEntity> findPricesByProductIdAndBrandIdAndDate(
            @Param("productId") Integer productId,
            @Param("brandId") Integer brandId,
            @Param("applicationDate") LocalDateTime applicationDate
    );
}