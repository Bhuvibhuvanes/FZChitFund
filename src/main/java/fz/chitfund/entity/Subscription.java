package fz.chitfund.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Users member;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ChitGroup chitGroup;
    
    private LocalDate joinDate;
    private String status;
    private Integer ticketNumber;

    // Getters and setters
} 