package com.salmontt.ms_sales.controller;

import com.salmontt.ms_sales.dto.ProductDTO;
import com.salmontt.ms_sales.dto.SaleRequestDTO;
import com.salmontt.ms_sales.model.Sale;
import com.salmontt.ms_sales.repository.SaleRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleRepository repo;
    private final RestTemplate restTemplate;

    public SaleController(SaleRepository repo) {
        this.repo = repo;
        this.restTemplate = new RestTemplate();
    }

    @PostMapping
    public Sale register(@RequestBody SaleRequestDTO request) {
        Long productId = request.getProductId();
        Integer cantidad = request.getCantidad();

        ProductDTO p = restTemplate.getForObject(
                "https://ms-products.onrender.com/api/products/" + productId,
                ProductDTO.class);

        if (p == null) {
            throw new RuntimeException("Producto no encontrado con ID " + productId);
        }

        Sale s = new Sale();
        s.setProductId(productId);
        s.setCantidad(cantidad);
        s.setTotal(p.getPrecio() * cantidad);

        return repo.save(s);
    }
    @GetMapping
    public List<Sale> getAll() {
        return repo.findAll();
    }
}
