package fz.chitfund.service;

import org.springframework.stereotype.Service;
import fz.chitfund.entity.*;
import fz.chitfund.repository.*;
import fz.chitfund.dto.ChitGroupRequest;
import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ChitGroupService {
    private final ChitGroupRepository chitGroupRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final AuctionRepository auctionRepository;
    private final UserRrepository userRepository;

    public ChitGroupService(ChitGroupRepository chitGroupRepository, 
                          SubscriptionRepository subscriptionRepository,
                          PaymentRepository paymentRepository,
                          AuctionRepository auctionRepository,
                          UserRrepository userRepository) {
        this.chitGroupRepository = chitGroupRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentRepository = paymentRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    // ChitGroup operations
    public ChitGroup createChitGroup(ChitGroup group) {
        return chitGroupRepository.save(group);
    }

    public List<ChitGroup> getAllGroups() {
        return chitGroupRepository.findAll();
    }

    public ChitGroup getGroupById(Long id) {
        return chitGroupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Chit Group not found"));
    }

    // Subscription operations
    public Subscription createSubscription(Long groupId, Long memberId) {
        ChitGroup group = getGroupById(groupId);
        Users member = userRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

        Subscription subscription = new Subscription();
        subscription.setChitGroup(group);
        subscription.setMember(member);
        subscription.setStatus("ACTIVE");
        
        return subscriptionRepository.save(subscription);
    }

    // Payment operations
    public Payment recordPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public List<Payment> getPaymentsBySubscription(Long subscriptionId) {
        return paymentRepository.findBySubscription_Id(subscriptionId);
    }

    // Auction operations
    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public List<Auction> getAuctionsByGroup(Long groupId) {
        return auctionRepository.findByChitGroup_Id(groupId);
    }

    public ChitGroup createNewChitGroup(ChitGroupRequest request) {
        ChitGroup group = new ChitGroup();
        group.setGroupName(request.getGroupName());
        group.setChitAmount(request.getChitAmount());
        group.setDuration(request.getDuration());
        group.setTotalMembers(request.getTotalMembers());
        group.setStartDate(request.getStartDate());
        group.setCommission(request.getCommission());
        group.setStatus("ACTIVE");
        
        Users agent = userRepository.findById(request.getAgentId())
            .orElseThrow(() -> new RuntimeException("Agent not found"));
        group.setAgent(agent);
        
        return chitGroupRepository.save(group);
    }

    public Subscription addMemberToGroup(Long groupId, Long memberId) {
        ChitGroup group = getGroupById(groupId);
        if (group.getSubscriptions().size() >= group.getTotalMembers()) {
            throw new RuntimeException("Group is full");
        }

        Users member = userRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

        Subscription subscription = new Subscription();
        subscription.setChitGroup(group);
        subscription.setMember(member);
        subscription.setJoinDate(LocalDate.now());
        subscription.setStatus("ACTIVE");
        subscription.setTicketNumber(group.getSubscriptions().size() + 1);

        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getGroupMembers(Long groupId) {
        return subscriptionRepository.findByChitGroup_Id(groupId);
    }

    public Auction placeBid(Long groupId, Long memberId, BigDecimal bidAmount) {
        ChitGroup group = getGroupById(groupId);
        Users member = userRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found"));

        Auction auction = new Auction();
        auction.setChitGroup(group);
        auction.setWinner(member);
        auction.setWinningBid(bidAmount);
        auction.setAuctionDate(LocalDate.now());
        
        return auctionRepository.save(auction);
    }
} 