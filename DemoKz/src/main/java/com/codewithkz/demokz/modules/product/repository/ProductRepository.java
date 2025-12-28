package com.codewithkz.demokz.modules.product.repository;

import com.codewithkz.demokz.modules.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
