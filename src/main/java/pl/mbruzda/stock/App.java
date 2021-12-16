package pl.mbruzda.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mbruzda.stock.productcatalog.ProductCatalog;
import pl.mbruzda.stock.productcatalog.ProductRepository;
import pl.mbruzda.stock.productcatalog.ProductStorage;

import java.math.BigDecimal;
import java.util.Arrays;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ProductCatalog createProductCatalog(
            ProductRepository productRepository) {
        ProductCatalog productCatalog = new ProductCatalog(productRepository);
        String productId1 = productCatalog.addProduct(
                "Example product 1",
                BigDecimal.valueOf(10.10),
                Arrays.asList("tag1", "tag2"),
                "https://picsum.photos/200/300"
        );
        productCatalog.publish(productId1);

        String productId2 = productCatalog.addProduct(
                "Example product 2",
                BigDecimal.valueOf(20.10),
                Arrays.asList("tag2"),
                "https://picsum.photos/300/200"
        );
        productCatalog.publish(productId2);

        return productCatalog;
    }
}