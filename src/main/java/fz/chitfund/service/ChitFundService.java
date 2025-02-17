package fz.chitfund.service;

import org.springframework.stereotype.Service;
import fz.chitfund.entity.*;
import fz.chitfund.repository.*;
import java.util.List;

@Service
public class ChitFundService {
    private final ChitSchemeRepository schemeRepository;
    private final ChitMemberRepository memberRepository;
    private final UserRrepository userRepository;

    public ChitFundService(ChitSchemeRepository schemeRepository, 
                          ChitMemberRepository memberRepository,
                          UserRrepository userRepository) {
        this.schemeRepository = schemeRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public ChitScheme createScheme(ChitScheme scheme) {
        return schemeRepository.save(scheme);
    }

    public ChitMember joinScheme(Long userId, Long schemeId) {
        Users user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        ChitScheme scheme = schemeRepository.findById(schemeId)
            .orElseThrow(() -> new RuntimeException("Scheme not found"));
        
        ChitMember member = new ChitMember();
        member.setUser(user);
        member.setScheme(scheme);
        member.setStatus("ACTIVE");
        
        return memberRepository.save(member);
    }

    public List<ChitScheme> getActiveSchemes() {
        return schemeRepository.findByStatus("ACTIVE");
    }

    public List<ChitMember> getMemberSchemes(Long userId) {
        return memberRepository.findByUser_Id(userId);
    }

    public ChitScheme getSchemeById(Long schemeId) {
        return schemeRepository.findById(schemeId)
            .orElseThrow(() -> new RuntimeException("Scheme not found"));
    }

    public List<ChitScheme> getAllSchemes() {
        return schemeRepository.findAll();
    }

    public ChitScheme updateScheme(Long schemeId, ChitScheme updatedScheme) {
        ChitScheme existingScheme = getSchemeById(schemeId);
        existingScheme.setSchemeName(updatedScheme.getSchemeName());
        existingScheme.setTotalAmount(updatedScheme.getTotalAmount());
        existingScheme.setDuration(updatedScheme.getDuration());
        existingScheme.setMemberCount(updatedScheme.getMemberCount());
        existingScheme.setMonthlyInstallment(updatedScheme.getMonthlyInstallment());
        existingScheme.setStatus(updatedScheme.getStatus());
        return schemeRepository.save(existingScheme);
    }
} 