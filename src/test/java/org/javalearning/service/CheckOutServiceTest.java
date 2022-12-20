package org.javalearning.service;

import org.javalearning.domain.checkout.Cart;
import org.javalearning.domain.checkout.CheckoutResponse;
import org.javalearning.domain.checkout.CheckoutStatus;
import org.javalearning.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.*;

class CheckOutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckOutService checkOutService = new CheckOutService(priceValidatorService);

    @Test
    void no_of_cores(){
        System.out.println("No of core : " + Runtime.getRuntime().availableProcessors());
    }


    @Test
    void parallelism(){
        System.out.println("Parallelism : " + ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    void checkout_6_items() {
        //given
        Cart cart = DataSet.createCart(6);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate() > 0.0);

    }

    @Test
    void checkout_17_items() {
        //given
        Cart cart = DataSet.createCart(17);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

    }

    @Test
    void checkout_25_items() {
        //given
        Cart cart = DataSet.createCart(25);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

    }

    @Test
    void modified_parallelism() {
        //given
       // System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","100");
        Cart cart = DataSet.createCart(100);

        //when
        CheckoutResponse checkoutResponse = checkOutService.checkout(cart);

        //then
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());

    }
}