
package com.click2vote.vote.repo;
import com.click2vote.common.domain.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
public interface VoteRepository extends JpaRepository<Vote, Long> {
    long countByPoll_IdAndOption_Id(Long pollId, Long optionId);
}
