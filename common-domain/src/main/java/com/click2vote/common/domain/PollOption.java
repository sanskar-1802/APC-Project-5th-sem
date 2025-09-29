package com.click2vote.common.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "poll_options")
public class PollOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)  // option text should not be null
    private String text;

// PollOption.java
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "poll_id", nullable = false)
// @JsonIgnore   //  prevents Poll from being serialized again
@JsonBackReference 
private Poll poll;


    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}
