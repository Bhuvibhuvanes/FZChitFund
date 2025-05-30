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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public BigDecimal getChitAmount() {
        return chitAmount;
    }

    public void setChitAmount(BigDecimal chitAmount) {
        this.chitAmount = chitAmount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(Integer totalMembers) {
        this.totalMembers = totalMembers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Users getAgent() {
        return agent;
    }

    public void setAgent(Users agent) {
        this.agent = agent;
    }

    public Set<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }
} 