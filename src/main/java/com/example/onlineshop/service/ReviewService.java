package com.example.onlineshop.service;

import com.example.onlineshop.DTO.ReviewDTO;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Review;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    public Optional<Page<ReviewDTO>> getReviewList(String productName){
        Sort sort = Sort.by(Sort.Order.asc("rating"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        Product product = productRepository.findByName(productName).get();
        Page<Review> page = reviewRepository.findAllByProduct(product, pageable);
        return Optional.of(page.map(ReviewDTO::from));
    }
}
