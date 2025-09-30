package com.click2vote.poll.web;

import com.click2vote.poll.service.PollService;
import com.click2vote.poll.dto.PollResponse;
import com.click2vote.poll.dto.OptionResponse;
import com.click2vote.common.domain.Poll;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/polls")
public class PollController {

    private final PollService svc;

    public PollController(PollService svc) {
        this.svc = svc;
    }

    // ------------------ Create Poll ------------------
    @PostMapping
    public PollResponse create(@RequestBody Map<String, Object> body) {
        String title = (String) body.get("title");
        String desc = (String) body.getOrDefault("description", "");

        @SuppressWarnings("unchecked")
        List<String> opts = (List<String>) body.getOrDefault("options", Collections.emptyList());

        // Save poll
        Poll poll = svc.create(title, desc, opts);

        // Map options with IDs
        List<OptionResponse> optionResponses = poll.getOptions().stream()
                .map(o -> new OptionResponse(o.getId(), o.getText()))
                .collect(Collectors.toList());

        return new PollResponse(
                poll.getId(),
                poll.getTitle(),
                poll.getDescription(),
                poll.isClosed(),
                optionResponses
        );
    }

    // ------------------ List Polls ------------------
    @GetMapping
    public List<PollResponse> list() {
        return svc.listOpen().stream()
                .map(p -> {
                    List<OptionResponse> optionResponses = p.getOptions().stream()
                            .map(o -> new OptionResponse(o.getId(), o.getText()))
                            .collect(Collectors.toList());
                    return new PollResponse(
                            p.getId(),
                            p.getTitle(),
                            p.getDescription(),
                            p.isClosed(),
                            optionResponses
                    );
                })
                .collect(Collectors.toList());
    }
}
