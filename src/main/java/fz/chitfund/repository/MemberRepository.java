package fz.chitfund.repository;

import fz.chitfund.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUser_Id(Long userId);
    List<Member> findByScheme_Id(Long schemeId);
}
