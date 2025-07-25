```mermaid
erDiagram
    BRAND {
        BIGINT id PK
        VARCHAR name
        VARCHAR description
    }

    PRODUCT {
        BIGINT id PK
        BIGINT ref_brand_id
        VARCHAR name
        VARCHAR description
        DECIMAL price
        BIGINT stock_quantity
        VARCHAR status
    }

    USER {
        BIGINT id PK
        VARCHAR login_id
        VARCHAR email
        DATE birth_date
        VARCHAR gender
    }

    LIKE {
        BIGINT id PK
        BIGINT ref_user_id
        VARCHAR target_type
        BIGINT target_id
    }

    LIKECOUNT {
        BIGINT id PK
        VARCHAR target_type
        BIGINT target_id
        BIGINT count
    }

    ORDER {
        BIGINT id PK
        VARCHAR status
    }

    ORDERLINE {
        BIGINT id PK
        BIGINT ref_order_id
        BIGINT ref_product_id
        VARCHAR name
        DECIMAL price
        BIGINT quantity
    }

    PAYMENT {
        BIGINT id PK
        BIGINT ref_order_id
        VARCHAR method
        VARCHAR status
        DECIMAL amount
        DATETIME payment_date
    }

    PRODUCT ||--|| BRAND : "brand_id"
    LIKE }|--|| USER : "user_id"
    ORDERLINE }|--|| ORDER : "order_id"
    ORDERLINE }|--|| PRODUCT : "product_id"
    PAYMENT }|--|| ORDER : "order_id"
```

![erd.png](erd.png)