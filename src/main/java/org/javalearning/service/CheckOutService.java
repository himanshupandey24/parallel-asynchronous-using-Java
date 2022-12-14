package org.javalearning.service;

import org.javalearning.domain.checkout.Cart;
import org.javalearning.domain.checkout.CartItem;
import org.javalearning.domain.checkout.CheckoutResponse;
import org.javalearning.domain.checkout.CheckoutStatus;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CheckOutService {

    private PriceValidatorService priceValidatorService;

    public CheckOutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart){

        CommonUtil.startTimer();

        List<CartItem> priceValidationList = cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> {
                    boolean isPriceInValid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(isPriceInValid);
                    return cartItem;
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());

        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();

        if(priceValidationList.size() > 0){
            LoggerUtil.log("Checkout Error");
            return new CheckoutResponse(CheckoutStatus.FAILURE, priceValidationList);
        }

        double finalRate = calculateFinalPrice_reduce(cart);
        LoggerUtil.log("Checkout Complete and the final rate is " + finalRate);

        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalRate);
    }

    private double calculateFinalPrice(Cart cart){
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double calculateFinalPrice_reduce(Cart cart){
        return cart.getCartItemList()
                .parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, Double::sum);
    }
}
