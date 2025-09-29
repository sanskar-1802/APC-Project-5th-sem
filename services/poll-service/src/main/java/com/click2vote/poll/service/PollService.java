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

        // Save and flush to force IDs to be generated
    Poll savedPoll = repo.saveAndFlush(p);

    // Optional: force loading options if using LAZY fetch
    savedPoll.getOptions().size(); // triggers loading

    return savedPoll;
    }

    public List<Poll> listOpen() {
        return repo.findAll().stream().filter(p -> !p.isClosed()).toList();
    }
}
