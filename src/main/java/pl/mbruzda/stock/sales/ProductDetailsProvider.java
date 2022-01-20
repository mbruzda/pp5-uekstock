package pl.mbruzda.stock.sales;

import pl.mbruzda.stock.productcatalog.Product;

public interface ProductDetailsProvider {
    ProductDetails getProductDetails(String productId);
}