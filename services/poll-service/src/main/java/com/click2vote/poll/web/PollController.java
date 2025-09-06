package com.click2vote.poll.web;

import com.click2vote.poll.service.PollService;
import com.click2vote.common.domain.Poll;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService svc;

    public PollController(PollService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Poll create(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String desc = (String) body.getOrDefault("description", "");

        @SuppressWarnings("unchecked")
        List<String> opts = (List<String>) body.getOrDefault("options", Collections.emptyList());

        return svc.create(title, desc, opts);
    }

    @GetMapping
    public List<Poll> list() {
        return svc.listOpen();
    }
}
