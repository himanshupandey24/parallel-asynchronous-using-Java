package org.javalearning.thread;

import org.javalearning.domain.Product;
import org.javalearning.domain.ProductInfo;
import org.javalearning.domain.Review;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.LoggerUtil;

import static org.javalearning.util.CommonUtil.stopWatch;


public class ProductServiceUsingThread {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;

    public ProductServiceUsingThread(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public Product retrieveProductDetails(String productId) throws InterruptedException {

        stopWatch.start();

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();


        stopWatch.stop();
        LoggerUtil.log("Total Time Taken : " + stopWatch.getTime());
        return new Product(productId, productInfoRunnable.productInfo, reviewRunnable.review);

    }

    public static void main(String[] args) throws InterruptedException{
        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingThread productService = new ProductServiceUsingThread(productInfoService, reviewService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        LoggerUtil.log("Product is " + product);
    }

    private class ProductInfoRunnable implements Runnable {

        private ProductInfo productInfo;
        private String productId;
        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }
    }

    private class ReviewRunnable implements Runnable {

        private String productId;
        private Review review;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        public Review getReview() {
            return review;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }
    }
}
