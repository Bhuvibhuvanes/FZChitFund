package fz.chitfund.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import fz.chitfund.dto.ChitGroupRequest;
import fz.chitfund.entity.*;
import fz.chitfund.service.ChitGroupService;
import java.util.List;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/groups")
@CrossOrigin(origins = "*")
@Tag(name = "Chit Group Management", description = "APIs for managing chit groups")
public class ChitGroupController {
    
    private final ChitGroupService chitGroupService;

    public ChitGroupController(ChitGroupService chitGroupService) {
        this.chitGroupService = chitGroupService;
    }

    @PostMapping
    public ResponseEntity<ChitGroup> createGroup(@RequestBody ChitGroup group) {
        return ResponseEntity.ok(chitGroupService.createChitGroup(group));
    }

    @GetMapping
    public ResponseEntity<List<ChitGroup>> getAllGroups() {
        return ResponseEntity.ok(chitGroupService.getAllGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChitGroup> getGroup(@PathVariable Long id) {
        return ResponseEntity.ok(chitGroupService.getGroupById(id));
    }

    @PostMapping("/{groupId}/subscriptions")
    public ResponseEntity<Subscription> subscribe(
            @PathVariable Long groupId, 
            @RequestParam Long memberId) {
        return ResponseEntity.ok(chitGroupService.createSubscription(groupId, memberId));
    }

    @PostMapping("/payments")
    public ResponseEntity<Payment> makePayment(@RequestBody Payment payment) {
        return ResponseEntity.ok(chitGroupService.recordPayment(payment));
    }

    @GetMapping("/subscriptions/{subscriptionId}/payments")
    public ResponseEntity<List<Payment>> getPayments(@PathVariable Long subscriptionId) {
        return ResponseEntity.ok(chitGroupService.getPaymentsBySubscription(subscriptionId));
    }

    @PostMapping("/auctions")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        return ResponseEntity.ok(chitGroupService.createAuction(auction));
    }

    @GetMapping("/{groupId}/auctions")
    public ResponseEntity<List<Auction>> getAuctions(@PathVariable Long groupId) {
        return ResponseEntity.ok(chitGroupService.getAuctionsByGroup(groupId));
    }

    @Operation(summary = "Create a new chit group")
    @PostMapping("/create")
    public ResponseEntity<ChitGroup> createChitGroup(@RequestBody ChitGroupRequest request) {
        return ResponseEntity.ok(chitGroupService.createNewChitGroup(request));
    }

    @Operation(summary = "Add a member to a chit group")
    @PostMapping("/{groupId}/members/add")
    public ResponseEntity<Subscription> addMember(
            @PathVariable Long groupId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(chitGroupService.addMemberToGroup(groupId, memberId));
    }

    @PostMapping("/{groupId}/auctions/bid")
    public ResponseEntity<Auction> placeBid(
            @PathVariable Long groupId,
            @RequestParam Long memberId,
            @RequestParam BigDecimal bidAmount) {
        return ResponseEntity.ok(chitGroupService.placeBid(groupId, memberId, bidAmount));
    }

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<Subscription>> getGroupMembers(@PathVariable Long groupId) {
        return ResponseEntity.ok(chitGroupService.getGroupMembers(groupId));
    }
} 