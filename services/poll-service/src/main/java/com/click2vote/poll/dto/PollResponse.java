package com.click2vote.poll.dto;

import java.util.List;

// DTO for poll
public class PollResponse {

    private Long id;
    private String title;
    private String description;
    private boolean closed;
    private List<OptionResponse> options; // Changed from List<String> to List<OptionResponse>

    public PollResponse(Long id, String title, String description, boolean closed, List<OptionResponse> options) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.closed = closed;
        this.options = options;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isClosed() { return closed; }
    public List<OptionResponse> getOptions() { return options; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setClosed(boolean closed) { this.closed = closed; }
    public void setOptions(List<OptionResponse> options) { this.options = options; }
}
