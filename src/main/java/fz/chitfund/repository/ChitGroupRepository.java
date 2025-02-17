package fz.chitfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fz.chitfund.entity.ChitGroup;
import java.util.List;

@Repository
public interface ChitGroupRepository extends JpaRepository<ChitGroup, Long> {
    List<ChitGroup> findByStatus(String status);
    List<ChitGroup> findByAgent_Id(Long agentId);
} 