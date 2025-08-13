package api.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_prices", schema = "plataforma_core")
@Getter
@Setter
public class PriceJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "brand_id")
    private Integer brandId;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "price_list")
    private Integer priceList;
    @Column(name = "priority")
    private Integer priority;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "currency")
    private String currency;
}