package org.javalearning.service;

import org.javalearning.domain.checkout.CartItem;
import org.javalearning.util.CommonUtil;
import org.javalearning.util.LoggerUtil;

public class PriceValidatorService {
    public boolean isCartItemInvalid(CartItem cartItem){
        int cartId = cartItem.getItemId();
        LoggerUtil.log("isCartItemInvalid : " + cartItem);
        CommonUtil.delay(500);

        if(cartId == 7 || cartId == 9 || cartId == 11)
            return true;

        return false;
    }
}
