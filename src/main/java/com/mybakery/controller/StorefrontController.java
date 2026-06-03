package com.mybakery.controller;

import com.mybakery.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Public-facing storefront controller.
 * Serves the promotional bakery homepage that customers see.
 * Only shows products marked as available — no admin controls here.
 */
@Controller
public class StorefrontController {

    private final ProductService productService;

    public StorefrontController(ProductService productService) {
        this.productService = productService;
    }

    /** GET / — bakery homepage with hero, about section, and product showcase. */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.findAvailable());
        model.addAttribute("productsByCategory", productService.findAvailableGroupedByCategory());
        return "public/index";
    }
}
