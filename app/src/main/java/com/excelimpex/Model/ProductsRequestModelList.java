package com.excelimpex.Model;

import java.io.Serializable;

/**
 * Created by rahil on 19/3/18.
 */

public class ProductsRequestModelList implements Serializable {
    String name;
    String nProductId;
    String sQty;
    String sAfterDiscount;
    String sDiscount;
    String sBeforeDiscount;
    String sName;
    String sAfterDiscountBase;
    String sBeforeDiscountBase;



    public ProductsRequestModelList(String name, String id, String qty, String subtotal, String discount, String beforediscount_subtotal, String sName, String sAfterDiscountBase, String sBeforeDiscountBase) {
        this.name=name;
        this.nProductId=id;

        this.sQty=qty;

        this.sAfterDiscount=subtotal;
        this.sDiscount=discount;
        this.sBeforeDiscount = beforediscount_subtotal;
        this.sName = sName;
        this.sAfterDiscountBase = sAfterDiscountBase;
        this.sBeforeDiscountBase = sBeforeDiscountBase;


    }






    public String getId() {
        return nProductId;
    }


    public String getName() {
        return name;
    }

    public String getQty() {
        return sQty;
    }
    public String getSubtotal() {
        return sAfterDiscount;
    }

    public String getDiscount() {
        return sDiscount;
    }

    public String getsName() {
        return sName;
    }

    public String getBeforediscount_subtotal() {
        return sBeforeDiscount;
    }
    public String getsAfterDiscountBase() {
        return sAfterDiscountBase;
    }

    public void setsAfterDiscountBase(String sAfterDiscountBase) {
        this.sAfterDiscountBase = sAfterDiscountBase;
    }

    public String getsBeforeDiscountBase() {
        return sBeforeDiscountBase;
    }

    public void setsBeforeDiscountBase(String sBeforeDiscountBase) {
        this.sBeforeDiscountBase = sBeforeDiscountBase;
    }
}
