package aichat.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AITraining {
    private int trainingId;
    private int productBaseId;
    private int variantId;
    private String productName;
    private String brandName;
    private String categoryName;
    private String color;
    private Integer rom;
    private BigDecimal price;
    private String specifications;
    private String description;
    private boolean isActive;
    private LocalDateTime createdAt;

    public AITraining(String productName) {
        this.productName = productName;
    }

    public AITraining(int trainingId, int productBaseId, int variantId, String productName, String brandName, String categoryName, String color, Integer rom, BigDecimal price, String specifications, String description, boolean isActive) {
        this.trainingId = trainingId;
        this.productBaseId = productBaseId;
        this.variantId = variantId;
        this.productName = productName;
        this.brandName = brandName;
        this.categoryName = categoryName;
        this.color = color;
        this.rom = rom;
        this.price = price;
        this.specifications = specifications;
        this.description = description;
        this.isActive = isActive;
    }

    
    
    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    

    
    public int getTrainingId() {
        return trainingId;
    }
    
    public void setTrainingId(int trainingId) {
        this.trainingId = trainingId;
    }
    
    public int getProductBaseId() {
        return productBaseId;
    }
    
    public void setProductBaseId(int productBaseId) {
        this.productBaseId = productBaseId;
    }
    
    public int getVariantId() {
        return variantId;
    }
    
    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getBrandName() {
        return brandName;
    }
    
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    public Integer getRom() {
        return rom;
    }
    
    public void setRom(Integer rom) {
        this.rom = rom;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getSpecifications() {
        return specifications;
    }
    
    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    
    @Override
    public String toString() {
        return "AITraining{" +
                "trainingId=" + trainingId +
                ", productBaseId=" + productBaseId +
                ", variantId=" + variantId +
                ", productName='" + productName + '\'' +
                ", brandName='" + brandName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", color='" + color + '\'' +
                ", rom=" + rom +
                ", price=" + price +
                ", isActive=" + isActive +
                ", createdAt=" + createdAt +
                '}';
    }
} 