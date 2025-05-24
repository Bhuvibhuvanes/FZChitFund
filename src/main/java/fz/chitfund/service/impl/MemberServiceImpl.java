package fz.chitfund.service.impl;

import fz.chitfund.dto.MemberRequest;
import fz.chitfund.entity.Member;
import fz.chitfund.entity.ChitScheme;
import fz.chitfund.entity.Users;
import fz.chitfund.repository.MemberRepository;
import fz.chitfund.repository.ChitSchemeRepository;
import fz.chitfund.repository.UsersRepository;
import fz.chitfund.repository.AddressRepository;
import fz.chitfund.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ChitSchemeRepository chitSchemeRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Member addMember(MemberRequest request) {
        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        ChitScheme scheme = null;
        if (request.getSchemeId() != null) {
            scheme = chitSchemeRepository.findById(request.getSchemeId())
                    .orElseThrow(() -> new RuntimeException("Scheme not found with id: " + request.getSchemeId()));
        }

        Member member = new Member();
        member.setUser(user);
        member.setScheme(scheme);
        member.setJoinDate(LocalDate.now());
        member.setStatus("ACTIVE");
        member.setMemberNumber(generateMemberNumber(request.getSchemeId()));
        
        // Set personal information
        member.setDateOfBirth(request.getDateOfBirth());
        member.setGender(request.getGender());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        member.setAadharNumber(request.getAadharNumber());
        member.setPanNumber(request.getPanNumber());

        return memberRepository.save(member);
    }

    @Override
    public Member addMemberWithoutScheme(MemberRequest request) {
        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));

        Member member = new Member();
        member.setUser(user);
        member.setJoinDate(LocalDate.now());
        member.setStatus("ACTIVE");
        member.setMemberNumber(generateMemberNumber(null));
        
        // Set personal information
        member.setDateOfBirth(request.getDateOfBirth());
        member.setGender(request.getGender());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setAddress(request.getAddress());
        member.setAadharNumber(request.getAadharNumber());
        member.setPanNumber(request.getPanNumber());

        return memberRepository.save(member);
    }

    private int generateMemberNumber(Long schemeId) {
        List<Member> existingMembers = memberRepository.findByScheme_Id(schemeId);
        return existingMembers.size() + 1;
    }

    @Override
    public List<Member> getMembersByUser(Long userId) {
        return memberRepository.findByUser_Id(userId);
    }

    @Override
    public List<Member> getMembersByScheme(Long schemeId) {
        return memberRepository.findByScheme_Id(schemeId);
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
    }

    @Override
    public void removeMember(Long id) {
        Member member = getMemberById(id);
        member.setStatus("INACTIVE");
        memberRepository.save(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
