package pl.mbruzda.stock.sales;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mbruzda.stock.sales.offerting.Offer;

@RestController
public class SalesController {

    public static final String CURRENT_CUSTOMER_ID = "Kuba";

    SalesFacade sales;

    public SalesController(SalesFacade salesFacade) {
        this.sales = salesFacade;
    }

    @PostMapping("/api/add-product/{productId}")
    public void addProductToBasket(@PathVariable String productId) {
        sales.addToBasket(getCurrentCustomerId(), productId);
    }

    @GetMapping("/api/current-offer")
    public Offer currentOffer() {
        return sales.getCurrentOffer(getCurrentCustomerId());
    }

    @PostMapping("/api/accept-offer")
    public void acceptOffer(@PathVariable String productId, CustomerData customerData) {
        sales.acceptOffer(getCurrentCustomerId(), customerData);
    }

    private String getCurrentCustomerId() {
        return CURRENT_CUSTOMER_ID;
    }

}