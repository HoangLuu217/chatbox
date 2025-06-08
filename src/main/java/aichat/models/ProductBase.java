package aichat.models;

import java.time.LocalDateTime;

public class ProductBase {
    private int productBaseId;
    private String productName;
    private int categoryId;
    private int brandId;
    private String description;
    private String specifications;

    private Category category;
    private Brand brand;

    private boolean active;
    private LocalDateTime createdAt;

    public ProductBase() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public ProductBase(String productName, Category category, Brand brand) {
        this();
        this.productName = productName;
        setCategory(category);
        setBrand(brand);
    }

    public int getProductBaseId() {
        return productBaseId;
    }

    public void setProductBaseId(int productBaseId) {
        this.productBaseId = productBaseId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.categoryId = category != null ? category.getCategoryId() : 0;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
        this.brandId = brand != null ? brand.getBrandId() : 0;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ProductBase{" +
                "productBaseId=" + productBaseId +
                ", productName='" + productName + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", description='" + description + '\'' +
                ", specifications='" + specifications + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}
