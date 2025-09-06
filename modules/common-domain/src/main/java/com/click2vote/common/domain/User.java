
package com.click2vote.common.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String passwordHash;
    private String displayName;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getUsername(){ return username; }
    public void setUsername(String username){ this.username = username; }
    public String getPasswordHash(){ return passwordHash; }
    public void setPasswordHash(String passwordHash){ this.passwordHash = passwordHash; }
    public String getDisplayName(){ return displayName; }
    public void setDisplayName(String displayName){ this.displayName = displayName; }
    public Set<String> getRoles(){ return roles; }
    public void setRoles(Set<String> roles){ this.roles = roles; }
}
