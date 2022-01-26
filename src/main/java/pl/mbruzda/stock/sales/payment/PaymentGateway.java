package pl.mbruzda.stock.sales.payment;

import pl.mbruzda.stock.sales.ordering.CustomerDetails;
import pl.mbruzda.stock.sales.ordering.PaymentDetails;

import java.math.BigDecimal;

public interface PaymentGateway {
    PaymentDetails register(String id, BigDecimal total, CustomerDetails customerDetails);
}