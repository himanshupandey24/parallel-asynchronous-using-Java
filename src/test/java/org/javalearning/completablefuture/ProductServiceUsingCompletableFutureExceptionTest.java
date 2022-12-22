package org.javalearning.completablefuture;

import org.javalearning.service.InventoryService;
import org.javalearning.service.ProductInfoService;
import org.javalearning.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceUsingCompletableFutureExceptionTest {

    @Mock
    ProductInfoService productInfoService;
    @Mock
    ReviewService reviewService;
    @Mock
    InventoryService inventoryService;

    @InjectMocks
    ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture;
    @Test
    void retrieveProductDetailsWithInventory_Approach2() {
        //given

        String productID = "ABC123";
    }
}