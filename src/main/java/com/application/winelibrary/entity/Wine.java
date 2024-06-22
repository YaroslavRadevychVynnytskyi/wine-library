package com.application.winelibrary.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "wines")
@NamedEntityGraph(
        name = "wine-with-foods",
        attributeNodes = @NamedAttributeNode("recommendedFood")
)
@SoftDelete
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String trademark;

    @Column(nullable = false)
    private String country;

    @Column(name = "country_flag_url")
    private String countryFlagUrl;

    @Column(nullable = false)
    private Year year;

    @Column(name = "liquid_volume", nullable = false)
    private Integer liquidVolume;

    @Column(name = "alcohol_content", nullable = false)
    private Integer alcoholContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "wine_type", columnDefinition = "varchar", nullable = false)
    private WineType wineType;

    @Column(name = "recommended_food", nullable = false)
    @ManyToMany
    @JoinTable(
            name = "wines_foods",
            joinColumns = @JoinColumn(name = "wine_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> recommendedFood;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private Sweetness sweetness;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar")
    private Acidity acidity;

    private String description;

    @Column(nullable = false)
    private Integer inventory;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "wine", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Rating> ratings;

    public enum WineType {
        RED,
        WHITE,
        ROSE,
        SPARKLING,
        DESSERT,
        FORTIFIED,
        FRUIT,
        BIODYNAMIC
    }

    public enum Sweetness {
        DRY,
        SEMI_DRY,
        SWEET,
        SEMI_SWEET,
        BRUT
    }

    public enum Acidity {
        LOW,
        MEDIUM,
        HIGH
    }
}
