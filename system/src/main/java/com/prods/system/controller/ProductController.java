package com.prods.system.controller;


import com.prods.system.domain.Product;
import com.prods.system.domain.dto.ProductDTO;
import com.prods.system.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repository;

    @PostMapping("/products")
    public ResponseEntity<Product> saveProdutos(@RequestBody @Valid ProductDTO dto){
        var product = new Product();
        BeanUtils.copyProperties(dto, product);
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(product));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = repository.findAll();
        if (!productList.isEmpty()){
            for (Product product : productList){
                UUID id = product.getId();
                product.add(linkTo(methodOn(ProductController.class).getOneProduct(id))
                        .withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Object> getOneProduct(@PathVariable(value = "id")UUID id){
        Optional<Product> produto = repository.findById(id);
        if (produto.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }

        produto.get().add(linkTo(methodOn(ProductController.class).getAllProducts())
                .withRel("Product List"));
        return ResponseEntity.status(HttpStatus.OK).body(produto.get());
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(value = "id") UUID id,
    @RequestBody @Valid ProductDTO productDTO){
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }

        var producto = product.get();
        BeanUtils.copyProperties(productDTO, producto);
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(producto));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Object> deleteProduto(@PathVariable(value = "id") UUID id){
        Optional<Product> product = repository.findById(id);
        if (product.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
        }

        repository.delete(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product has been deleted");
    }

}
