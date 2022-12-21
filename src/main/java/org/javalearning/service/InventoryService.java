package org.javalearning.service;

import org.javalearning.domain.Inventory;
import org.javalearning.domain.ProductOption;
import org.javalearning.util.CommonUtil;

public class InventoryService {
    public Inventory retrieveInventory(ProductOption productOption){
        CommonUtil.delay(500);
        return Inventory.builder().count(2).build();
    }
}
