
package com.click2vote.poll;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.click2vote"})
public class PollServiceApp {
    public static void main(String[] args){ SpringApplication.run(PollServiceApp.class, args); }
}
