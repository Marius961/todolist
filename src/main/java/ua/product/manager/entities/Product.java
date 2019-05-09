package ua.product.manager.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Seller seller;

    @NotBlank
    @Column(unique = true)
    @Size(min = 4, max = 64)
    private String name;

    @Size(max = 512)
    private String description;

    private String imageName;

    private boolean active;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MeasurementUnit_id")
    private MeasurementUnit measurementUnit;

    @NotNull
    private double priceForMeasurementUnit;

    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    private long ordersCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public MeasurementUnit getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(MeasurementUnit measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public double getPriceForMeasurementUnit() {
        return priceForMeasurementUnit;
    }

    public void setPriceForMeasurementUnit(double priceForMeasurementUnit) {
        this.priceForMeasurementUnit = priceForMeasurementUnit;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public long getOrdersCount() {
        return ordersCount;
    }

    public void setOrdersCount(long ordersCount) {
        this.ordersCount = ordersCount;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }


}
