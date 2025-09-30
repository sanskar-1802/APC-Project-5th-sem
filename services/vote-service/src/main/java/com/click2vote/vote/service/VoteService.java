package com.click2vote.vote.service;

import com.click2vote.vote.repo.VoteRepository;
import com.click2vote.common.domain.Vote;
import com.click2vote.common.domain.Poll;
import com.click2vote.common.domain.PollOption;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.*;

@Service
public class VoteService {
    private final VoteRepository repo;
    private final EntityManager em;
    private final ExecutorService vt = Executors.newVirtualThreadPerTaskExecutor();
    private volatile boolean accepting = true;

    public VoteService(VoteRepository repo, EntityManager em) {
        this.repo = repo;
        this.em = em;
    }

    @Transactional
    public Vote cast(Vote v) {
        if (!accepting) throw new IllegalStateException("voting closed");

        // attach references so Hibernate doesn't try to INSERT new Poll/Option
        Poll pollRef = em.getReference(Poll.class, v.getPoll().getId());
        PollOption optionRef = em.getReference(PollOption.class, v.getOption().getId());

        v.setPoll(pollRef);
        v.setOption(optionRef);

        return repo.save(v);
    }

    public CompletableFuture<Map<Long, Long>> tallyAsync(Long pollId, List<Long> optionIds) {
        return CompletableFuture.supplyAsync(() -> {
            Map<Long, Long> res = new HashMap<>();
            if (optionIds == null || optionIds.isEmpty()) {
                // safer default instead of throwing
                return res;
            }
            for (Long id : optionIds) {
                long count = repo.countByPoll_IdAndOption_Id(pollId, id);
                res.put(id, count);
            }
            return res;
        }, vt);
    }

    public void toggleAccepting(boolean on) {
        synchronized (this) { this.accepting = on; }
    }
}
