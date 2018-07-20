package com.excelimpex.Model;

import java.io.Serializable;

/**
 * Created by rahil on 19/3/18.
 */

public class ProductsRequestModelListApi implements Serializable {

    String nProductId;
    String nCatId;
    String sQty;
    String sAfterDiscount;
    String sDiscount;
    String sBeforeDiscount;
    String nTotalWithoutGST;




    public ProductsRequestModelListApi(String id, String nCatId,String qty, String subtotal, String discount, String beforediscount_subtotal, String nTotalWithoutGST) {

        this.nProductId=id;
        this.nCatId = nCatId;

        this.sQty=qty;

        this.sAfterDiscount=subtotal;
        this.sDiscount=discount;
        this.sBeforeDiscount = beforediscount_subtotal;
        this.nTotalWithoutGST = nTotalWithoutGST;

    }

    public String getnTotalWithoutGST() {
        return nTotalWithoutGST;
    }

    public String getId() {
        return nProductId;
    }

    public String getnCatId() {
        return nCatId;
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


    public String getBeforediscount_subtotal() {
        return sBeforeDiscount;
    }
}
