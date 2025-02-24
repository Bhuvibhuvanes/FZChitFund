package fz.chitfund.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ChitGroupRequest {
    private String groupName;
    private BigDecimal chitAmount;
    private Integer duration;
    private Integer totalMembers;
    private LocalDate startDate;
    private BigDecimal commission;
    private Long agentId;

    // Getters and setters
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

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
} 