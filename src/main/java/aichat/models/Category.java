package aichat.models;

public class Category {
    private int categoryId;
    private String categoryName;
    private Integer parentCategoryId;
    private Category parentCategory;
    
    public Category() {
    }
    
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Category(String categoryName, Category parentCategory) {
        this(categoryName);
        this.parentCategory = parentCategory;
        this.parentCategoryId = parentCategory != null ? parentCategory.getCategoryId() : null;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public Integer getParentCategoryId() {
        return parentCategoryId;
    }
    
    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
    
    public Category getParentCategory() {
        return parentCategory;
    }
    
    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
        this.parentCategoryId = parentCategory != null ? parentCategory.getCategoryId() : null;
    }
    
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", parentCategoryId=" + parentCategoryId +
                '}';
    }
} 