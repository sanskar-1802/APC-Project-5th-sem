package com.click2vote.vote.dto;

public class VoteRequest {
    private Long pollId;
    private Long optionId;
    private Long userId;

    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }

    public Long getOptionId() { return optionId; }
    public void setOptionId(Long optionId) { this.optionId = optionId; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
