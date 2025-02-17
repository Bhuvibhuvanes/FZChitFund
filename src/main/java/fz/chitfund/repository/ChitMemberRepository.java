package fz.chitfund.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fz.chitfund.entity.ChitMember;
import java.util.List;

@Repository
public interface ChitMemberRepository extends JpaRepository<ChitMember, Long> {
    List<ChitMember> findByUser_Id(Long userId);
    List<ChitMember> findByScheme_Id(Long schemeId);
} 