package org.javalearning.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductOption {
    private Integer productOptionId;
    private String size;
    private String color;
    private double price;
    private Inventory inventory;

    public ProductOption(Integer productOptionId, String size, String color, double price){
        this.productOptionId = productOptionId;
        this.size = size;
        this.color = color;
        this.price = price;
    }


}
