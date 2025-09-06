
package com.click2vote.common.domain;

import jakarta.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@Table(name = "polls")
public class Poll {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Instant openAt = Instant.now();
    private Instant closeAt;
    private boolean closed = false;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<PollOption> options = new ArrayList<>();

    public void addOption(PollOption o){
        o.setPoll(this);
        options.add(o);
    }
    // getters/setters
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public String getTitle(){ return title; }
    public void setTitle(String title){ this.title = title; }
    public String getDescription(){ return description; }
    public void setDescription(String description){ this.description = description; }
    public Instant getOpenAt(){ return openAt; }
    public void setOpenAt(Instant openAt){ this.openAt = openAt; }
    public Instant getCloseAt(){ return closeAt; }
    public void setCloseAt(Instant closeAt){ this.closeAt = closeAt; }
    public boolean isClosed(){ return closed; }
    public void setClosed(boolean closed){ this.closed = closed; }
    public List<PollOption> getOptions(){ return options; }
    public void setOptions(List<PollOption> options){ this.options = options; }
}
