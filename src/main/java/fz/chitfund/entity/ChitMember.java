package fz.chitfund.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ChitMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private Users user;
    
    @ManyToOne
    private ChitScheme scheme;
    
    private LocalDate joinDate;
    private String status; // ACTIVE, INACTIVE
    private Integer memberNumber;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public ChitScheme getScheme() {
        return scheme;
    }

    public void setScheme(ChitScheme scheme) {
        this.scheme = scheme;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(Integer memberNumber) {
        this.memberNumber = memberNumber;
    }
} 