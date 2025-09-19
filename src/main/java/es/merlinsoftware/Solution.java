package es.merlinsoftware;

import es.merlinsoftware.pojo.ProductSales;
import es.merlinsoftware.pojo.ProductStock;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static List<Long> sortProductsByScores(int stockWeight, int salesWeight,
                                                  List<ProductStock> productsStockInformation,
                                                  List<ProductSales> productsSalesInformation) {
        if(productsSalesInformation==null || productsStockInformation==null){
            return new ArrayList<>();
        }
        Map<Long,Double> salesMap = productsSalesInformation.stream().collect(Collectors.toMap(ProductSales::getProductId,ProductSales::getSalesAmount));
        Map<Long,Long> stockMap = productsStockInformation.stream().collect(Collectors.toMap(ProductStock::getProductId,ProductStock::getAvailableStock));

        Map<Long,Double> weights=new HashMap<>();
        for(Long productId:stockMap.keySet()){
            if(salesMap.containsKey(productId)){
                double sales=salesMap.get(productId);
                Long stock=stockMap.get(productId);
                double weight=(stockWeight*stock)+(salesWeight*sales);
                weights.put(productId,weight);
            }
        }

        return weights.entrySet().stream().sorted((a,b)->Double.compare(b.getValue(),a.getValue())).map(Map.Entry::getKey).collect(Collectors.toList());
    }

}
