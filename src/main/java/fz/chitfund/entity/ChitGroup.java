package fz.chitfund.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chit_groups")
public class ChitGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String groupName;
    private BigDecimal chitAmount;
    private Integer duration;
    private Integer totalMembers;
    private LocalDate startDate;
    private String status;
    private BigDecimal commission;
    
    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Users agent;
    
    @OneToMany(mappedBy = "chitGroup", cascade = CascadeType.ALL)
    private Set<Subscription> subscriptions = new HashSet<>();

    // Getters and setters
} 