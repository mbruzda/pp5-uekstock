package pl.mbruzda.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.mbruzda.stock.productcatalog.Image;
import pl.mbruzda.stock.productcatalog.ImagesStorage;
import pl.mbruzda.stock.productcatalog.ProductCatalogFacade;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ProductCatalogFacade productCatalogFacade(ImagesStorage imagesStorage){
        return new ProductCatalogFacade();
    }

    @Bean
    public ImagesStorage imagesStorage(){
        return new ImagesStorage();
    }
}

