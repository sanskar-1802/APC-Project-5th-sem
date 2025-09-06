
package com.click2vote.poll.service;
import com.click2vote.poll.repo.PollRepository;
import com.click2vote.common.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class PollService {
    private final PollRepository repo;
    public PollService(PollRepository repo){ this.repo = repo; }
    @Transactional
    public Poll create(String title, String desc, List<String> options){
        Poll p = new Poll(); p.setTitle(title); p.setDescription(desc);
        options.stream().map(s -> { PollOption o = new PollOption(); o.setText(s); return o; }).forEach(p::addOption);
        return repo.save(p);
    }
    public List<Poll> listOpen(){ return repo.findAll().stream().filter(p -> !p.isClosed()).toList(); }
}
