create database AI

use AI 
drop database AI

CREATE TABLE [Categories] (
  [CategoryID] INT IDENTITY(1,1) PRIMARY KEY,
  [CategoryName] NVARCHAR(100) NOT NULL,
  [ParentCategoryID] INT,
  [IsActive] BIT DEFAULT (1),
  FOREIGN KEY ([ParentCategoryID]) REFERENCES [Categories]([CategoryID])
);

CREATE TABLE [Brands] (
  [BrandID] INT IDENTITY(1,1) PRIMARY KEY,
  [BrandName] NVARCHAR(100) UNIQUE NOT NULL,
  [IsActive] BIT DEFAULT (1)
);

CREATE TABLE [ProductBase] (
  [ProductBaseID] INT IDENTITY(1,1) PRIMARY KEY,
  [ProductName] NVARCHAR(200) NOT NULL,
  [CategoryID] INT NOT NULL,
  [BrandID] INT NOT NULL,
  [Description] NVARCHAR(MAX),
  [Specifications] NVARCHAR(MAX),
  [IsActive] BIT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  FOREIGN KEY ([CategoryID]) REFERENCES [Categories]([CategoryID]),
  FOREIGN KEY ([BrandID]) REFERENCES [Brands]([BrandID])
);

CREATE TABLE [ProductVariants] (
  [VariantID] INT IDENTITY(1,1) PRIMARY KEY,
  [ProductBaseID] INT NOT NULL,
  [Color] NVARCHAR(50) NOT NULL,
  [RAM] INT,
  [ROM] INT,
  [SKU] NVARCHAR(50) UNIQUE NOT NULL,
  [Price] DECIMAL(18,2) NOT NULL,
  [StockQuantity] INT DEFAULT (0),
  [ImageURLs] NVARCHAR(MAX),
  [IsActive] BIT DEFAULT (1),
  FOREIGN KEY ([ProductBaseID]) REFERENCES [ProductBase]([ProductBaseID])
);










CREATE TABLE [AITraining] (
  [TrainingID] INT IDENTITY(1,1) PRIMARY KEY,
  [ProductBaseID] INT NOT NULL,
  [VariantID] INT NOT NULL,
  [ProductName] NVARCHAR(200) NOT NULL,
  [BrandName] NVARCHAR(100) NOT NULL,
  [CategoryName] NVARCHAR(100) NOT NULL,
  [Color] NVARCHAR(50),
  [RAM] INT,
  [ROM] INT,
  [Price] DECIMAL(18,2),
  [Specifications] NVARCHAR(MAX),
  [Description] NVARCHAR(MAX),
  [IsActive] BIT DEFAULT (1),
  [CreatedAt] DATETIME DEFAULT (GETDATE()),
  FOREIGN KEY ([ProductBaseID]) REFERENCES [ProductBase]([ProductBaseID]),
  FOREIGN KEY ([VariantID]) REFERENCES [ProductVariants]([VariantID])
);

INSERT INTO Brands (BrandName, IsActive) VALUES
(N'Apple', 1),
(N'samsung', 1),
(N'xiaomi', 1);

INSERT INTO Categories (CategoryName, ParentCategoryID, IsActive) VALUES
(N'điện thoại', NULL, 1),
(N'android', 2, 1),
(N'iphone', 2, 1),
(N'tablet', NULL, 1),
(N'New Category', NULL, 1),
(N'New Category', NULL, 1),
(N'New Category', NULL, 1),
(N'New Category', NULL, 1);

INSERT INTO ProductBase (ProductName, CategoryID, BrandID, Description, Specifications, IsActive) VALUES
(N'iphone 15', 2, 1, N'chụp ảnh đẹp, hiệu năng cao , xu hướng', N'giới trẻ ưa thích', 1),
(N'iphone 15 pro max', 2, 1, N'nặng , hiệu năng cao , sản phẩm được giới trẻ sử dụng nhiều và chụp ảnh đẹp', N'giới trẻ yêu thích nhất', 1),
(N'iphone 16', 4, 1, NULL, NULL, 1),
(N'iphone 15 pro', 2, 1, NULL, NULL, 1),
(N'iphone 14 pro max', 4, 1, N'giá cả phù hợp , độ phân giải 48mp', N'phù hợp cho người thích chụp ảnh', 1);



INSERT INTO [AI].[dbo].[ProductVariants] (
    ProductBaseID,
    Color,
    RAM,
    ROM,
    SKU,
    Price,
    StockQuantity,
    ImageURLs,
    IsActive
)
VALUES
(1, N'xanh', 16, 64, N'0', 20000000.00, 0, N'https://cdn2.cellphones.com.vn/insecure/rs:fill:358:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/i/p/iphone-16-1.png', 1),
(1, N'titan trắng', 16, 128, N'2', 12000000.00, 2, N'https://didongxanh.com.vn/_next/image?url=https%3A%2F%2Fdidongxanh.com.vn%2Fwp-content%2Fuploads%2F2024%2F05%2Fiphone-15-pro-max-titan-1.png&w=256&q=75', 1),
(2, N'trắng', 32, 256, N'8', 27000000.00, 15, N'https://didongxanh.com.vn/_next/image?url=https%3A%2F%2Fdidongxanh.com.vn%2Fwp-content%2Fuploads%2F2024%2F05%2Fiphone-15-pro-max-trang-1-1.png&w=640&q=75', 1),
(5, N'tím', 8, 512, N'22', 30000000.00, 10, N'https://didongxanh.com.vn/_next/image?url=https%3A%2F%2Fdidongxanh.com.vn%2Fwp-content%2Fuploads%2F2024%2F05%2F3-2.jpg&w=256&q=75', 1);




CREATE TRIGGER trg_Insert_AITraining
ON ProductVariants
AFTER INSERT
AS
BEGIN
  SET NOCOUNT ON;

  INSERT INTO AITraining (
    ProductBaseID,
    VariantID,
    ProductName,
    BrandName,
    CategoryName,
    Color,
    RAM,
    ROM,
    Price,
    Specifications,
    Description,
    IsActive,
    CreatedAt
  )
  SELECT 
    pb.ProductBaseID,
    i.VariantID,
    pb.ProductName,
    b.BrandName,
    c.CategoryName,
    i.Color,
    i.RAM,
    i.ROM,
    i.Price,
    pb.Specifications,
    pb.Description,
    i.IsActive,
    GETDATE()
  FROM INSERTED i
  JOIN ProductBase pb ON i.ProductBaseID = pb.ProductBaseID
  JOIN Brands b ON pb.BrandID = b.BrandID
  JOIN Categories c ON pb.CategoryID = c.CategoryID;
END;


GO

-- Trigger cập nhật AITraining khi sửa ProductBase
CREATE OR ALTER TRIGGER trg_Update_ProductBase_AITraining
ON ProductBase
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE a
    SET 
        a.ProductName = i.ProductName,
        a.Specifications = i.Specifications,
        a.Description = i.Description,
        a.IsActive = i.IsActive
    FROM AITraining a
    INNER JOIN inserted i ON a.ProductBaseID = i.ProductBaseID
    INNER JOIN ProductVariants pv ON a.VariantID = pv.VariantID
    WHERE pv.IsActive = 1;
END
GO

-- Trigger cập nhật AITraining khi sửa ProductVariant
CREATE OR ALTER TRIGGER trg_Update_ProductVariant_AITraining
ON ProductVariants
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    UPDATE a
    SET 
        a.Color = i.Color,
        a.RAM = i.RAM,
        a.ROM = i.ROM,
        a.Price = i.Price,
        a.IsActive = i.IsActive
    FROM AITraining a
    INNER JOIN inserted i ON a.VariantID = i.VariantID
    INNER JOIN ProductBase pb ON a.ProductBaseID = pb.ProductBaseID
    WHERE pb.IsActive = 1;
END
GO

-- Trigger xóa/vô hiệu hóa dữ liệu AITraining khi xóa/vô hiệu hóa sản phẩm
CREATE OR ALTER TRIGGER trg_Delete_ProductBase_AITraining
ON ProductBase
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;
    
    IF UPDATE(IsActive)
    BEGIN
        UPDATE a
        SET a.IsActive = 0
        FROM AITraining a
        INNER JOIN inserted i ON a.ProductBaseID = i.ProductBaseID
        WHERE i.IsActive = 0;
    END
END
GO 