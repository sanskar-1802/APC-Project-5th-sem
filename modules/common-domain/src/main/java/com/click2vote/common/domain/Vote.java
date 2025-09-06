
package com.click2vote.common.domain;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "votes", uniqueConstraints = @UniqueConstraint(columnNames = {"poll_id","user_id"}))
public class Vote {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) private Poll poll;
    @ManyToOne(optional=false) private PollOption option;
    @Column(name="user_id", nullable=false) private Long userId;
    private Instant castAt = Instant.now();

    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    public Poll getPoll(){ return poll; }
    public void setPoll(Poll poll){ this.poll = poll; }
    public PollOption getOption(){ return option; }
    public void setOption(PollOption option){ this.option = option; }
    public Long getUserId(){ return userId; }
    public void setUserId(Long userId){ this.userId = userId; }
    public Instant getCastAt(){ return castAt; }
    public void setCastAt(Instant castAt){ this.castAt = castAt; }
}
