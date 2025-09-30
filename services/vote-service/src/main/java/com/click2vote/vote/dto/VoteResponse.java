package com.click2vote.vote.dto;
public class VoteResponse {
    private Long id;
    private Long pollId;
    private Long optionId;
    private Long userId;
    public VoteResponse(Long id, Long pollId, Long optionId, Long userId) {
        this.id=id; this.pollId=pollId; this.optionId=optionId; this.userId=userId;
    }
    public Long getId(){return id;} public Long getPollId(){return pollId;} public Long getOptionId(){return optionId;} public Long getUserId(){return userId;}
}

