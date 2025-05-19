package fz.chitfund.service;

import fz.chitfund.entity.ChitGroup;
import fz.chitfund.entity.ChitMember;
import fz.chitfund.entity.Payment;
import fz.chitfund.repository.ChitGroupRepository;
import fz.chitfund.repository.ChitMemberRepository;
import fz.chitfund.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    
    private final PaymentRepository paymentRepository;
    private final ChitGroupRepository chitGroupRepository;
    private final ChitMemberRepository chitMemberRepository;

    public PaymentService(
        PaymentRepository paymentRepository, 
        ChitGroupRepository chitGroupRepository, 
        ChitMemberRepository chitMemberRepository) {
        this.paymentRepository = paymentRepository;
        this.chitGroupRepository = chitGroupRepository;
        this.chitMemberRepository = chitMemberRepository;
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    public Payment save(Long memberId,Long groupId,Payment payment) {
        ChitGroup chitGroup = chitGroupRepository.findById(groupId)
            .orElseThrow(() -> new RuntimeException("ChitGroup not found"));
        ChitMember chitMember = chitMemberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("ChitMember not found"));
        payment.setChitGroup(chitGroup);
        payment.setChitMember(chitMember);
        return paymentRepository.save(payment);
    }

    public Payment update(Long id, Payment payment) {
        Payment existingPayment = findById(id);
        // Update fields
        existingPayment.setAmount(payment.getAmount());
        existingPayment.setDueDate(payment.getDueDate());
        existingPayment.setPaidDate(payment.getPaidDate());
        existingPayment.setStatus(payment.getStatus());
        return paymentRepository.save(existingPayment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    public Object save(Payment payment, Long groupId, Long memberId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
} 