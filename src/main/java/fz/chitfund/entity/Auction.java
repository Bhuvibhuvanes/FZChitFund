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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChitGroup getChitGroup() {
        return chitGroup;
    }

    public void setChitGroup(ChitGroup chitGroup) {
        this.chitGroup = chitGroup;
    }

    public Users getWinner() {
        return winner;
    }

    public void setWinner(Users winner) {
        this.winner = winner;
    }

    public Integer getAuctionNumber() {
        return auctionNumber;
    }

    public void setAuctionNumber(Integer auctionNumber) {
        this.auctionNumber = auctionNumber;
    }

    public LocalDate getAuctionDate() {
        return auctionDate;
    }

    public void setAuctionDate(LocalDate auctionDate) {
        this.auctionDate = auctionDate;
    }

    public BigDecimal getDividendAmount() {
        return dividendAmount;
    }

    public void setDividendAmount(BigDecimal dividendAmount) {
        this.dividendAmount = dividendAmount;
    }

    public BigDecimal getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(BigDecimal winningBid) {
        this.winningBid = winningBid;
    }
} 