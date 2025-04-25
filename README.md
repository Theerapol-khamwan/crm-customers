ดึงยอดขายสูงสุดของลูกค้าแต่ละรายในปีที่ผ่านมา

SQL จะช่วยดึงข้อมูลยอดขายสูงสุด (MaxSaleAmount) ของแต่ละลูกค้าในช่วง 1 ปีที่ผ่านมา พร้อมแสดงวันที่ทำรายการ และจัดอันดับลูกค้าตามยอดขายจากมากไปน้อย

💡 จุดประสงค์

ค้นหายอดขายสูงสุดของแต่ละ CustomerId ในช่วง 1 ปีล่าสุด (ย้อนหลังจากวันที่รันสคริปต์)

แสดงผลลัพธ์พร้อมวันที่และจำนวนยอดขายสูงสุด

จัดอันดับลูกค้าตามยอดขาย (Rank สูงสุด = ลูกค้าที่มียอดขายมากที่สุด)

🔧 โครงสร้าง SQL

```
WITH RankedSales AS (
    SELECT
        CustomerId,
        SaleDate,
        SaleAmount,
        ROW_NUMBER() OVER(
            PARTITION BY CustomerId
            ORDER BY SaleAmount DESC, SaleDate DESC
        ) AS rn
    FROM Sales
    WHERE SaleDate >= DATEADD(year, -1, GETDATE())
),
CustomerMaxSaleDetails AS (
    SELECT
        CustomerId,
        SaleDate,
        SaleAmount
    FROM RankedSales
    WHERE rn = 1
)
SELECT
    cmsd.CustomerId,
    cmsd.SaleDate,
    cmsd.SaleAmount    AS MaxSaleAmount,
    DENSE_RANK() OVER (
        ORDER BY cmsd.SaleAmount DESC
    )                  AS SalesRank
FROM CustomerMaxSaleDetails cmsd
ORDER BY
    SalesRank ASC,
    cmsd.SaleAmount DESC;
```

คำอธิบาย:

1.RankedSales: ใช้ ROW_NUMBER() แบ่งกลุ่มตาม CustomerId และเรียงยอดขาย (SaleAmount) จากมากไปน้อย ถ้าเท่ากันจะดูวันที่ (SaleDate) ล่าสุดก่อน

2.CustomerMaxSaleDetails: กรองเฉพาะแถวที่ rn = 1 ซึ่งหมายถึงยอดขายสูงสุดต่อ 1 ลูกค้า

3.ผลลัพธ์หลัก: ดึงข้อมูลจาก CTE สอง และใช้ DENSE_RANK() จัดลำดับลูกค้าตามยอดขายสูงสุด จากนั้นเรียงลำดับผลลัพธ์ให้อยู่ในลำดับที่ถูกต้อง

