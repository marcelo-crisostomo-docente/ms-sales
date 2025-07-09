package com.salmontt.ms_sales.repository;

import com.salmontt.ms_sales.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}
