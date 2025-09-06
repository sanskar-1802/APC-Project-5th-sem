
package com.click2vote.poll.repo;
import com.click2vote.common.domain.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PollRepository extends JpaRepository<Poll, Long> {}
