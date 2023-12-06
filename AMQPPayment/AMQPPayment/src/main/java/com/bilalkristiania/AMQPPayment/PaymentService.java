package com.bilalkristiania.AMQPPayment;

public interface PaymentService {
    void processOrderEvent(OrderEvent orderEvent);
    //boolean processPayment(double amount);

    boolean processPayment(OrderEvent orderEvent);

    double checkBalance();

    Payment getPaymentById(Long id);

    Payment savePayment(Payment payment);

    Payment getInternalPayment(Long id);

    Payment saveInternalPayment(Payment payment);
}