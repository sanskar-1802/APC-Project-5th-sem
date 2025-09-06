package com.click2vote.common.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "votes")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private PollOption option;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }

    public PollOption getOption() { return option; }
    public void setOption(PollOption option) { this.option = option; }
}
