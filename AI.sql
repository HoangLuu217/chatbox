create database AI

use AI 


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