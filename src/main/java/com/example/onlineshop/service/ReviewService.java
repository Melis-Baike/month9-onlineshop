package com.example.onlineshop.service;

import com.example.onlineshop.DTO.FeedbackDTO;
import com.example.onlineshop.DTO.ReviewDTO;
import com.example.onlineshop.DTO.UserDTO;
import com.example.onlineshop.entity.Product;
import com.example.onlineshop.entity.Review;
import com.example.onlineshop.entity.User;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.ReviewRepository;
import com.example.onlineshop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Optional<Page<ReviewDTO>> getReviewList(String productName){
        Sort sort = Sort.by(Sort.Order.asc("rating"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        Product product = productRepository.findByName(productName).get();
        Page<Review> page = reviewRepository.findAllByProduct(product, pageable);
        return Optional.of(page.map(ReviewDTO::from));
    }

    public ReviewDTO setReview(FeedbackDTO feedbackDTO, HttpSession session){
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        User user = userRepository.findByEmail(userDTO.getEmail()).get();
        Product product = productRepository.findByName(feedbackDTO.getProductName()).get();
        Optional<Review> optionalReview = reviewRepository.findByUserAndProduct(user, product);
        if(optionalReview.isPresent()){
            optionalReview.get().setText(feedbackDTO.getText());
            optionalReview.get().setRating(feedbackDTO.getRating());
            reviewRepository.setTextAndRatingById(optionalReview.get().getText(), optionalReview.get().getRating(),
                    optionalReview.get().getId());
            return ReviewDTO.from(optionalReview.get());
        } else {
            Review review = Review.builder()
                    .text(feedbackDTO.getText())
                    .rating(feedbackDTO.getRating())
                    .user(user)
                    .product(product)
                    .build();
            reviewRepository.save(review);
            return ReviewDTO.from(review);
        }
    }
}
