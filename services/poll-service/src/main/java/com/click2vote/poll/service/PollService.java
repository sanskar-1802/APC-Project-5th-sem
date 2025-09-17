package com.click2vote.poll.service;

import com.click2vote.poll.repo.PollRepository;
import com.click2vote.common.domain.Poll;
import com.click2vote.common.domain.PollOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PollService {

    private final PollRepository repo;

    public PollService(PollRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Poll create(String title, String desc, List<String> options) {
        Poll p = new Poll();
        p.setTitle(title);
        p.setDescription(desc);

        for (String s : options) {
            PollOption o = new PollOption();
            o.setText(s);
            p.addOption(o);  // ensures bidirectional link
        }

        return repo.save(p);
    }

    public List<Poll> listOpen() {
        return repo.findAll().stream().filter(p -> !p.isClosed()).toList();
    }
}
