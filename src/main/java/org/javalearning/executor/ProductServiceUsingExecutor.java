package org.javalearning.executor;

import org.javalearning.domain.Product;
import org.javalearning.domain.ProductInfo;
import org.javalearning.domain.Review;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.LoggerUtil;

import static org.javalearning.util.CommonUtil.stopWatch;


public class ProductServiceUsingExecutor {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId){

        stopWatch.start();

        ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); //blocking call
        Review review = reviewService.retrieveReviews(productId); //blocking call

        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfo, review);

    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        LoggerUtil.log("Product is " + product);
    }

}
