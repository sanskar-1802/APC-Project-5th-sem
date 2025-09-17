
// package com.click2vote.poll;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// @SpringBootApplication(scanBasePackages = {"com.click2vote"})
// public class PollServiceApp {
//     public static void main(String[] args){ SpringApplication.run(PollServiceApp.class, args); }
// }
package com.click2vote.poll;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.click2vote"})
@EntityScan(basePackages = {"com.click2vote.common.domain"})   // 👈 scans for your Poll entity
@EnableJpaRepositories(basePackages = {"com.click2vote.poll.repo"}) // 👈 scans for your repositories
public class PollServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(PollServiceApp.class, args);
    }
}

