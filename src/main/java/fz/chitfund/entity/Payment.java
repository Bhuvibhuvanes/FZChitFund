package fz.chitfund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;
    
    private Integer installmentNumber;
    private LocalDate dueDate;
    private LocalDate paidDate;
    private BigDecimal amount;
    private String status;

    // Getters and setters
} 