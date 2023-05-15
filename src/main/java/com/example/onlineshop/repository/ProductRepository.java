package com.example.onlineshop.repository;

import com.example.onlineshop.entity.Brand;
import com.example.onlineshop.entity.Category;
import com.example.onlineshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Product d SET d.quantity = :quantity WHERE d.id = :id")
    void setQuantityById(@Param("quantity") Long quantity, @Param("id") Long id);


    default Page<Product> searchProducts(Optional<String> name, Optional<String> minPrice, Optional<String> maxPrice,
                                         Optional<Category> category, Optional<String> quantity, Optional<Brand> brand,
                                         Optional<String> description, Pageable pageable) {
        if (maxPrice.isPresent() && minPrice.isPresent() && !maxPrice.get().isBlank() && !minPrice.get().isBlank()
                && Double.parseDouble(maxPrice.get()) < Double.parseDouble(minPrice.get())) {
            return Page.empty();
        }

        return findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            name.ifPresent(s -> {
                if(!s.isBlank()) {
                    predicates.add(criteriaBuilder.equal(root.get("name"), s));
                }
            });

            quantity.ifPresent(qnt -> {
                if(!qnt.isBlank()){
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("quantity"), Integer.parseInt(qnt)));
                }
            });

            description.ifPresent(descr -> {
                if(!descr.isBlank()){
                    predicates.add(criteriaBuilder.like(root.get("description"), "%" + descr + "%"));
                }
            });

            minPrice.ifPresent(price -> {
                if(!price.isBlank()) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(price)));
                }
            });
            maxPrice.ifPresent(price -> {
                if(!price.isBlank()) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(price)));
                }
            });

            category.ifPresent(cat -> predicates.add(criteriaBuilder.equal(root.get("category"), cat)));

            brand.ifPresent(br -> predicates.add(criteriaBuilder.equal(root.get("brand"), br)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
