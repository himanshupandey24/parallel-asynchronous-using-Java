package org.javalearning.completablefuture;

import org.javalearning.domain.Product;
import org.javalearning.domain.ProductInfo;
import org.javalearning.domain.Review;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static org.javalearning.util.CommonUtil.stopWatch;


public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId){

        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture  = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(
                        reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)
                ).join(); // this is going to block the thread

        //ProductInfo productInfo = productInfoService.retrieveProductInfo(productId); //blocking call
        //Review review = reviewService.retrieveReviews(productId); //blocking call

        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTime());

        return product;

    }

    public CompletableFuture<Product>  retrieveProductDetailsCf(String productId){
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture  = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));

        CompletableFuture<Product> productCF = productInfoCompletableFuture
                .thenCombine(
                        reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review)
                );

        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTime());

        return productCF;

    }

    public static void main(String[] args) {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new
                ProductServiceUsingCompletableFuture(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        LoggerUtil.log("Product is " + product);
    }

}
