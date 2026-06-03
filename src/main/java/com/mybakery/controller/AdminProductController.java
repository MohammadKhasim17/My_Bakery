package com.mybakery.controller;

import com.mybakery.model.Product;
import com.mybakery.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Admin-only web controller for managing bakery products.
 * All routes live under {@code /admin/products} — separate from the public storefront.
 */
@Controller
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }

    /** GET /admin/products — admin product list with edit/delete actions. */
    @GetMapping
    public String listProducts(Model model) {
        model.addAttribute("products", productService.findAll());
        return "admin/products/list";
    }

    /** GET /admin/products/new — form to add a new product. */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "admin/products/form";
    }

    /** POST /admin/products — save a newly created product. */
    @PostMapping
    public String createProduct(@Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/products/form";
        }

        String imagePath = handleImageUpload(imageFile);
        if (imagePath != null) {
            product.setImagePath(imagePath);
        }

        productService.save(product);
        redirectAttributes.addFlashAttribute("successMessage", "Product added successfully!");
        return "redirect:/admin/products";
    }

    /** GET /admin/products/{id}/edit — form pre-filled with existing product data. */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("isEdit", true);
        return "admin/products/form";
    }

    /** POST /admin/products/{id} — update an existing product. */
    @PostMapping("/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute("product") Product product,
                                BindingResult bindingResult,
                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "admin/products/form";
        }

        String imagePath = handleImageUpload(imageFile);
        if (imagePath != null) {
            product.setImagePath(imagePath);
        }

        productService.update(id, product);
        redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
        return "redirect:/admin/products";
    }

    /** POST /admin/products/{id}/delete — remove a product. */
    @PostMapping("/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        productService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        return "redirect:/admin/products";
    }

    private String handleImageUpload(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        if (fileName.contains("..")) {
            throw new IllegalArgumentException("Invalid file path in image upload.");
        }

        try {
            Path uploadDir = Paths.get("uploads");
            Files.createDirectories(uploadDir);

            String storedFileName = System.currentTimeMillis() + "-" + fileName;
            Path targetLocation = uploadDir.resolve(storedFileName);
            try (InputStream inputStream = imageFile.getInputStream()) {
                Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/product-images/" + storedFileName;
        } catch (IOException ex) {
            throw new RuntimeException("Failed to store image file.", ex);
        }
    }
}
