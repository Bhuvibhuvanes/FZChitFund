package fz.chitfund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "auctions")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private ChitGroup chitGroup;
    
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Users winner;
    
    private Integer auctionNumber;
    private LocalDate auctionDate;
    private BigDecimal dividendAmount;
    private BigDecimal winningBid;

    // Getters and setters
} 