
package com.click2vote.vote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.click2vote"})
public class VoteServiceApp {
    public static void main(String[] args){ SpringApplication.run(VoteServiceApp.class, args); }
}
