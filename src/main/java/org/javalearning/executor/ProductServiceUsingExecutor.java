package org.javalearning.executor;

import org.javalearning.domain.Product;
import org.javalearning.domain.ProductInfo;
import org.javalearning.domain.Review;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.LoggerUtil;

import java.util.concurrent.*;

import static org.javalearning.util.CommonUtil.stopWatch;


public class ProductServiceUsingExecutor {

    private ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingExecutor(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws ExecutionException, InterruptedException, TimeoutException {

        stopWatch.start();

        Future<ProductInfo> productInfoFuture = executorService.submit(()->productInfoService.retrieveProductInfo(productId));
        Future<Review> reviewFuture = executorService.submit(()->reviewService.retrieveReviews(productId));

        ProductInfo productInfo = productInfoFuture.get(2, TimeUnit.SECONDS);
        Review review = reviewFuture.get(2, TimeUnit.SECONDS);

        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTime());
        executorService.shutdown();
        return new Product(productId, productInfo, review);

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingExecutor productService = new ProductServiceUsingExecutor(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        LoggerUtil.log("Product is " + product);
    }

}
