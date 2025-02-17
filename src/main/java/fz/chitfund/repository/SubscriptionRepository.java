package fz.chitfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fz.chitfund.entity.Subscription;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByMember_Id(Long memberId);
    List<Subscription> findByChitGroup_Id(Long groupId);
} 