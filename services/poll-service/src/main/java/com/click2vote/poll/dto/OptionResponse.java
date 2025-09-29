package com.click2vote.poll.dto;

// Immutable DTO for poll options
public class OptionResponse {
    private Long id;
    private String text;

    public OptionResponse(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    public Long getId() { return id; }
    public String getText() { return text; }

    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
}
