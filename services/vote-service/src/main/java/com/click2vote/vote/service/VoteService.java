
package com.click2vote.vote.service;
import com.click2vote.vote.repo.VoteRepository;
import com.click2vote.common.domain.Vote;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.concurrent.*;
@Service
public class VoteService {
    private final VoteRepository repo;
    private final ExecutorService vt = Executors.newVirtualThreadPerTaskExecutor();
    private volatile boolean accepting = true;
    public VoteService(VoteRepository repo){ this.repo = repo; }
    @Transactional public Vote cast(Vote v){
        if(!accepting) throw new IllegalStateException("voting closed");
        return repo.save(v);
    }
public CompletableFuture<Map<Long,Long>> tallyAsync(Long pollId, List<Long> optionIds) {
    return CompletableFuture.supplyAsync(() -> {
        Map<Long,Long> res = new HashMap<>();
        for (Long id : optionIds) {
            long count = repo.countByPollIdAndOptionId(pollId, id);
            res.put(id, count);
        }
        return res;
    }, vt);
}

    public void toggleAccepting(boolean on){
        synchronized(this){ this.accepting = on; }
    }
    

}
