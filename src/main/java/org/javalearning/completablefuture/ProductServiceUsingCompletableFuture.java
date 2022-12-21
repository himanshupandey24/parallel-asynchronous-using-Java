package org.javalearning.completablefuture;

import org.javalearning.domain.*;
import org.javalearning.service.InventoryService;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.javalearning.util.CommonUtil.stopWatch;


public class ProductServiceUsingCompletableFuture {
    private final ProductInfoService productInfoService;
    private final ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
    }

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService,
                                                ReviewService reviewService,
                                                InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
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

    public Product retrieveProductDetailsWithInventory(String productId){

        CommonUtil.startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() ->productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventoryToProductOption(productInfo));
                            return productInfo;
                        });

        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,(productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        CommonUtil.timeTaken();

        return product;
    }

    public Product retrieveProductDetailsWithInventory_Approach2(String productId){

        CommonUtil.startTimer();
        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() ->productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            productInfo.setProductOptions(updateInventoryToProductOption_Approach2(productInfo));
                            return productInfo;
                        });

        CompletableFuture<Review> reviewCompletableFuture =
                CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(productId));

        Product product = productInfoCompletableFuture
                .thenCombine(reviewCompletableFuture,(productInfo, review) -> new Product(productId, productInfo, review))
                .join();

        CommonUtil.timeTaken();

        return product;
    }

    private List<ProductOption> updateInventoryToProductOption(ProductInfo productInfo) {

        return productInfo.getProductOptions()
                .stream()
                .peek(productOption -> {
                    Inventory inventory = inventoryService.retrieveInventory(productOption);
                    productOption.setInventory(inventory);
                })
                .toList();
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

    private List<ProductOption> updateInventoryToProductOption_Approach2(ProductInfo productInfo) {

        List<CompletableFuture<ProductOption>> productOptions = productInfo.getProductOptions()
                .stream()
                .map(productOption ->
                        CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                                .thenApply(inventory -> {
                                    productOption.setInventory(inventory);
                                    return productOption;
                                }))
                .toList();

        return productOptions.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
