package org.javalearning.service;

import org.javalearning.domain.ProductInfo;
import org.javalearning.domain.ProductOption;

import java.util.List;

import static org.javalearning.util.CommonUtil.delay;

public class ProductInfoService {
    public ProductInfo retrieveProductInfo(String productId){

        delay(1000);

        List<ProductOption> productOptions = List.of(
                new ProductOption(1, "128GB", "BLACK", 699.99),
                new ProductOption(2,"256GB","RED", 749.99)
                );

        return ProductInfo.builder().productId(productId).productOptions(productOptions).build();

    }
}
