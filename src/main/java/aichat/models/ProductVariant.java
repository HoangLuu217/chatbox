package aichat.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductVariant {
    private int variantId;
    private int productBaseId;
    private String color;
    private Integer rom;
    private BigDecimal price;
    private int stockQuantity;
    private List<String> imageUrls;

    private boolean active;
    private LocalDateTime createdAt;

    public ProductVariant() {
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public ProductVariant(String color, Integer rom, BigDecimal price, int stockQuantity, List<String> imageUrls) {
        this.color = color;
        this.rom = rom;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.imageUrls = imageUrls;
    }


    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getProductBaseId() {
        return productBaseId;
    }

    public void setProductBaseId(int productBaseId) {
        this.productBaseId = productBaseId;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
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
        return "ProductVariant{" +
                "variantId=" + variantId +
                ", productBaseId=" + productBaseId +
                ", color='" + color + '\'' +
                ", rom=" + rom +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imageUrls=" + imageUrls +
                ", isActive=" + active +
                ", createdAt=" + createdAt +
                '}';
    }
}