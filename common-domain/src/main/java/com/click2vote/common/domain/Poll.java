package com.click2vote.common.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean closed = false;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollOption> options = new ArrayList<>();

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isClosed() { return closed; }
    public void setClosed(boolean closed) { this.closed = closed; }

    public List<PollOption> getOptions() { return options; }
    public void setOptions(List<PollOption> options) { this.options = options; }

    // --- Needed for PollService ---
    public void addOption(PollOption option) {
        option.setPoll(this); // set back-reference
        this.options.add(option);
    }
}
