package fz.chitfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fz.chitfund.entity.ChitScheme;
import java.util.List;

@Repository
public interface ChitSchemeRepository extends JpaRepository<ChitScheme, Long> {
    List<ChitScheme> findByStatus(String status);
    List<ChitScheme> findByAdmin_Id(Long adminId);
} 