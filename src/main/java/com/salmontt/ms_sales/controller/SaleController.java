package com.salmontt.ms_sales.controller;
import com.salmontt.ms_sales.dto.ProductDTO;
import com.salmontt.ms_sales.model.Sale;
import com.salmontt.ms_sales.repository.SaleRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    public Sale register(@RequestParam Long productId, @RequestParam Integer cantidad) {
        // Consumir el microservicio de productos
        ProductDTO p = restTemplate.getForObject(
                "https://ms-products.fly.dev/api/products/" + productId, ProductDTO.class);

        Sale s = new Sale();
        s.setProductId(productId);
        s.setCantidad(cantidad);
        s.setTotal(p.getPrecio() * cantidad);

        return repo.save(s);
    }
}

