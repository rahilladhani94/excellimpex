package com.excelimpex.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by rahil on 19/3/18.
 */

public class ProductsRequestModel implements Serializable {

    //String name;
    String nSalesPersonId;
    String nDistributorId;
    String sTotal;



    private List<ProductsRequestModelListApi> sProducts = null;

//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getId() {
        return nSalesPersonId;
    }

    public void setId(String id) {
        this.nSalesPersonId = id;
    }
    public String getsTotal() {
        return sTotal;
    }

    public void setsTotal(String sTotal) {
        this.sTotal = sTotal;
    }
    public String getnDistributorId() {
        return nDistributorId;
    }

    public void setnDistributorId(String nDistributorId) {
        this.nDistributorId = nDistributorId;
    }


    public List<ProductsRequestModelListApi> getsProducts() {
        return sProducts;
    }

    public void setsProducts(List<ProductsRequestModelListApi> sProducts) {
        this.sProducts = sProducts;
    }
}
