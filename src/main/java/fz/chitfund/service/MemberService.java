package fz.chitfund.service;

import fz.chitfund.dto.MemberRequest;
import fz.chitfund.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

public interface MemberService {
    Member addMember(MemberRequest request);
    List<Member> getMembersByUser(Long userId);
    List<Member> getMembersByScheme(Long schemeId);
    Member getMemberById(Long id);
    void removeMember(Long id);
    List<Member> getAllMembers();
    Member addMemberWithoutScheme(MemberRequest request);
}
