package com.bilalkristiania.AMQPPayment;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImplementation implements PaymentService{

    private double balance = 4000000.0;

    private Payment payment;

    private final PaymentRepository paymentRepository;


    // remember to check for if user/order exists.
    // might need to parse the Double to BigDecimal instead
    @Override
    public void processOrderEvent(OrderEvent orderEvent) {
        double orderAmount = Double.parseDouble(orderEvent.getAmount());
        log.info("Processing Order Event: {}", orderEvent);
        processPayment(orderEvent);
        log.info("Processed Order Event: {}", orderEvent);
    }

    @Override
    public boolean processPayment(OrderEvent orderEvent) {
        if (payment == null) {
            // Initialize the payment entity with an initial balance if not exists
            payment = new Payment();
            payment.setBalance(BigDecimal.valueOf(1000000.0));
        }
        double amount = Double.parseDouble(orderEvent.getAmount());
        BigDecimal currentBalance = payment.getBalance();
        if (currentBalance.compareTo(BigDecimal.valueOf(amount)) >= 0) {
            // Sufficient funds, update the balance
            payment.setBalance(currentBalance.subtract(BigDecimal.valueOf(amount)));
            orderEvent.setStatus("goodFunds");
            log.info("Payment successful. Remaining balance: {}", payment.getBalance());
            return true;  // Payment successful
        }
        orderEvent.setStatus("badFunds");
        log.warn("Insufficient funds for payment. Current balance: {}", currentBalance);
        return false;  // Insufficient funds
    }

    @Override
    public double checkBalance() {
        if (payment == null) {
            return 0.0;  // No account, return 0 balance
        }
        return payment.getBalance().doubleValue();
    }

    @Override
    public Payment getPaymentById(Long id) {
        return null;
    }

    @Override
    public Payment savePayment(Payment payment) {
        return null;
    }

    @Override
    public Payment getInternalPayment(Long id) {
        return payment;
    }

    @Override
    public Payment saveInternalPayment(Payment newPayment) {
        if (payment != null) {
            // Update the existing payment entity
            payment.setBalance(newPayment.getBalance());
        } else {
            payment = newPayment;
        }
        return payment;
    }


}
