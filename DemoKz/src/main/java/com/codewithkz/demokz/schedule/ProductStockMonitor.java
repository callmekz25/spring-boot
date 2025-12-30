package com.codewithkz.demokz.schedule;

import com.codewithkz.demokz.modules.product.dto.ProductDto;
import com.codewithkz.demokz.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j

public class ProductStockMonitor {
    private final ProductService productService;

    public ProductStockMonitor(ProductService productService) {
        this.productService = productService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void CheckOutOfStockProducts() {
        List<ProductDto> products = productService.GetProductsOutOfStock();

        if (products.isEmpty()) {
            return;
        }

        products.forEach(p ->
                log.warn("Product [{} - {}] is out of stock",
                        p.getId(), p.getName())
        );
    }
}
