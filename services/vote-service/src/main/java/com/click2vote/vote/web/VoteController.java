package com.click2vote.vote.web;

import com.click2vote.vote.service.VoteService;
import com.click2vote.common.domain.Vote;
import com.click2vote.common.domain.Poll;
import com.click2vote.common.domain.PollOption;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    private final VoteService svc;

    public VoteController(VoteService svc) {
        this.svc = svc;
    }

    @PostMapping
    public Vote cast(@RequestBody Map<String, Object> body) {
        Vote v = new Vote();
        Poll p = new Poll();
        p.setId(((Number) body.get("pollId")).longValue());
        PollOption o = new PollOption();
        o.setId(((Number) body.get("optionId")).longValue());
        v.setPoll(p);
        v.setOption(o);
        v.setUserId(((Number) body.get("userId")).longValue());
        return svc.cast(v);
    }

@GetMapping("/{pollId}/tally")
public CompletableFuture<Map<Long, Long>> tally(
        @PathVariable Long pollId,
        @RequestParam(required = false) List<Long> options) {
    return svc.tallyAsync(pollId, options != null ? options : Collections.emptyList());
}




    @GetMapping("/{pollId}/export")
    public void exportCsv(@PathVariable Long pollId, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=results-" + pollId + ".csv");

        try (PrintWriter w = resp.getWriter()) {
            w.println("optionId,count");
            // Example with real results:
            // Map<Long, Long> results = svc.tallyAsync(pollId, Collections.emptyList()).join();
            // results.forEach((opt, count) -> w.println(opt + "," + count));
        }
    }
}
