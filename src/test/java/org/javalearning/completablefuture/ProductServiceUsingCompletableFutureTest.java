package org.javalearning.completablefuture;

import org.javalearning.domain.Product;
import org.javalearning.service.InventoryService;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.javalearning.util.CommonUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceUsingCompletableFutureTest {

    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();

    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture =
            new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    void retrieveProductDetails() {
        //given
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsCf(){
        //given
        String productId = "ABC123";

        //when
        CompletableFuture<Product> productCf = productServiceUsingCompletableFuture.retrieveProductDetailsCf(productId);

        //then

        productCf.thenAccept(product -> {
            assertNotNull(product);
            assertTrue(product.getProductInfo().getProductOptions().size()>0);
            assertNotNull(product.getReview());
        }).join();

    }

    @Test
    void retrieveProductDetailsWithInventory(){
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        product.getProductInfo().getProductOptions().forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventory_Approach2(){
        String productId = "ABC123";

        //when
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_Approach2(productId);

        //then
        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size()>0);
        product.getProductInfo().getProductOptions().forEach(productOption -> assertNotNull(productOption.getInventory()));
        assertNotNull(product.getReview());
    }
}