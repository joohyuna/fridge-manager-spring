package com.lineacademy.fridgemanagerspring.domain.product;


import com.lineacademy.fridgemanagerspring.domain.category.Category;
import com.lineacademy.fridgemanagerspring.domain.common.BaseTimeEntity;
import com.lineacademy.fridgemanagerspring.domain.enums.ProductStatus;
import com.lineacademy.fridgemanagerspring.domain.enums.StorageType;
import com.lineacademy.fridgemanagerspring.domain.enums.Unit;
import com.lineacademy.fridgemanagerspring.domain.fridge.Fridge;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String memo;

    @Column(name = "storage_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StorageType storageType = StorageType.ROOM_TEMP;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Unit unit = Unit.G;

    @Column()
    private Integer price;

    @Column(nullable = false, name = "expiration_date")
    private LocalDate expirationDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.STORED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fridge_id", nullable = false)
    private Fridge fridge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public Product(
            String name,
            String memo,
            StorageType storageType,
            Double quantity,
            Unit unit,
            Integer price,
            LocalDate expirationDate,
            ProductStatus status,
            Fridge fridge,
            Category category
    ) {
        this.name = name;
        this.memo = memo;
        this.storageType = (storageType != null) ? storageType : StorageType.ROOM_TEMP;
        this.quantity = quantity;
        this.unit = (unit != null) ? unit : Unit.G;
        this.price = price;
        this.expirationDate = expirationDate;
        this.status = (status != null) ? status : ProductStatus.STORED;
        this.fridge = fridge;
        this.category = category;

    }
}
