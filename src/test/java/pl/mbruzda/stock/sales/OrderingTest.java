package pl.mbruzda.stock.sales;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import pl.mbruzda.stock.sales.offerting.Offer;
import pl.mbruzda.stock.sales.offerting.OfferMaker;
import pl.mbruzda.stock.sales.ordering.InMemoryReservationStorage;
import pl.mbruzda.stock.sales.ordering.Reservation;
import pl.mbruzda.stock.sales.ordering.ReservationDetails;
import pl.mbruzda.stock.sales.payment.DummyPaymentGateway;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class OrderingTest {

    public static final int EXPECTED_LINES_COUNT = 1;
    private BasketStorage basketStorage;
    private DummyProductDetailsProvider productDetailsProvider;
    private InMemoryReservationStorage reservationStorage;

    @BeforeEach
    void initializeSharedObjects() {
        basketStorage = new BasketStorage();
        productDetailsProvider = new DummyProductDetailsProvider();
        reservationStorage = new InMemoryReservationStorage();
    }

    @Test
    void itAllowsProceedOrder() {
        //Arrange
        String productId = thereIsProduct("product-1", BigDecimal.valueOf(10.10));
        String customerId = thereIsCustomer();
        SalesFacade sales = thereIsSalesModule();

        //Act
        sales.addToBasket(customerId, productId);
        Offer currentOffer = sales.getCurrentOffer(customerId);
        CustomerData customerData = clientProvidedCustomerDetails();
        ReservationDetails reservationDetails = sales.acceptOffer(customerId, customerData);

        itContainsPaymentUrl(reservationDetails);
        thereIsPendingReservationWithId(reservationDetails.getReservationId());
        reservationWithIdContainsCustomerDetails(reservationDetails.getReservationId(), customerData);
        reservationWithIdContainsXLines(reservationDetails.getReservationId(), EXPECTED_LINES_COUNT);
    }

    private void reservationWithIdContainsXLines(String reservationId, int expectedLinesCount) {
        Reservation reservation = reservationStorage.load(reservationId).get();
        assertEquals(expectedLinesCount, reservation.getLinesCount());
    }

    private void reservationWithIdContainsCustomerDetails(String reservationId, CustomerData customerData) {
        Reservation reservation = reservationStorage.load(reservationId).get();
        assertEquals(reservation.getCustomerEmail(), customerData.getEmail());
    }

    private void thereIsPendingReservationWithId(String reservationId) {
        Optional<Reservation> reservation = reservationStorage.load(reservationId);
        assertTrue(reservation.isPresent());

        Reservation loadedReservation = reservation.get();
        assertTrue(loadedReservation.isPending(), "reservation is in invalid state");
    }

    private void itContainsPaymentUrl(ReservationDetails reservationDetails) {
        assertNotNull(reservationDetails.getPaymentUrl());
        assertTrue(reservationDetails.getPaymentUrl().contains(DummyPaymentGateway.DUMMY_PAYMENT_URL), "invalid payment url");
    }

    private CustomerData clientProvidedCustomerDetails() {
        return new CustomerData("examle@example.pl", "john", "doe");
    }

    private String thereIsProduct(String productId, BigDecimal price) {
        ProductDetails product = new ProductDetails(productId, price);
        productDetailsProvider.products.put(productId, product);
        return productId;
    }

    private String thereIsCustomer() {
        return UUID.randomUUID().toString();
    }

    private SalesFacade thereIsSalesModule() {
        return new SalesFacade(
                basketStorage,
                productDetailsProvider,
                new OfferMaker(productDetailsProvider),
                reservationStorage, new DummyPaymentGateway());
    }
}