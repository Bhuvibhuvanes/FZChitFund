package fz.chitfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fz.chitfund.entity.Auction;
import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByChitGroup_Id(Long groupId);
    List<Auction> findByWinner_Id(Long winnerId);
} 