package aichat.models;

import java.math.BigDecimal;
import java.util.List;

public class ProductVariant extends BaseModel {
    private int variantId;
    private int productBaseId;
    private String color;
    private Integer ram;
    private Integer rom;
    private String sku;
    private BigDecimal price;
    private int stockQuantity;
    private List<String> imageUrls;
    
    private ProductBase productBase;
    
    public ProductVariant() {
        super();
    }
    
    public ProductVariant(ProductBase productBase, String color, String sku, BigDecimal price) {
        this();
        setProductBase(productBase);
        this.color = color;
        this.sku = sku;
        this.price = price;
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
    
    public Integer getRam() {
        return ram;
    }
    
    public void setRam(Integer ram) {
        this.ram = ram;
    }
    
    public Integer getRom() {
        return rom;
    }
    
    public void setRom(Integer rom) {
        this.rom = rom;
    }
    
    public String getSku() {
        return sku;
    }
    
    public void setSku(String sku) {
        this.sku = sku;
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
    
    public ProductBase getProductBase() {
        return productBase;
    }
    
    public void setProductBase(ProductBase productBase) {
        this.productBase = productBase;
        this.productBaseId = productBase != null ? productBase.getProductBaseId() : 0;
    }
    
    @Override
    public String toString() {
        return "ProductVariant{" +
                "variantId=" + variantId +
                ", productBaseId=" + productBaseId +
                ", color='" + color + '\'' +
                ", ram=" + ram +
                ", rom=" + rom +
                ", sku='" + sku + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imageUrls=" + imageUrls +
                ", isActive=" + isActive() +
                '}';
    }
} 