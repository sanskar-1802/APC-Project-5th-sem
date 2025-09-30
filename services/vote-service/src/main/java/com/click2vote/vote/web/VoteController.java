package com.click2vote.vote.web;

import com.click2vote.vote.service.VoteService;
import com.click2vote.common.domain.Vote;
import com.click2vote.common.domain.Poll;
import com.click2vote.common.domain.PollOption;
import com.click2vote.vote.dto.VoteRequest;
import com.click2vote.vote.dto.VoteResponse;

import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/vote")  // singular to match your Postman
@CrossOrigin(origins = "*")
public class VoteController {

    private final VoteService svc;

    public VoteController(VoteService svc) {
        this.svc = svc;
    }


    @PostMapping
@CrossOrigin(origins = "http://localhost:5173") // allow frontend origin (or "*")
public VoteResponse cast(@RequestBody VoteRequest request) {
    Vote v = new Vote();
    v.setUserId(request.getUserId());

    Poll p = new Poll(); p.setId(request.getPollId());
    v.setPoll(p);

    PollOption o = new PollOption(); o.setId(request.getOptionId());
    v.setOption(o);

    Vote saved = svc.cast(v);

    return new VoteResponse(saved.getId(), request.getPollId(), request.getOptionId(), request.getUserId());
}



    // Get tally of votes for a poll
    @GetMapping("/{pollId}/tally")
    public CompletableFuture<Map<Long, Long>> tally(
            @PathVariable Long pollId,
            @RequestParam(required = false) List<Long> options) {
        return svc.tallyAsync(pollId, options != null ? options : Collections.emptyList());
    }

    // Export poll results as CSV
    @GetMapping("/{pollId}/export")
    public void exportCsv(@PathVariable Long pollId, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/csv");
        resp.setHeader("Content-Disposition", "attachment; filename=results-" + pollId + ".csv");

        try (PrintWriter w = resp.getWriter()) {
            w.println("optionId,count");
            Map<Long, Long> results = svc.tallyAsync(pollId, Collections.emptyList()).join();
            results.forEach((optId, count) -> w.println(optId + "," + count));
        }
    }
}
