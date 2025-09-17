
// package com.click2vote.vote;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// @SpringBootApplication(scanBasePackages = {"com.click2vote"})
// public class VoteServiceApp {
//     public static void main(String[] args){ SpringApplication.run(VoteServiceApp.class, args); }
// }

package com.click2vote.vote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.click2vote"})
@EntityScan(basePackages = {"com.click2vote.common.domain"})   // 👈 Scan entities
@EnableJpaRepositories(basePackages = {"com.click2vote.vote.repo"}) // 👈 Scan repos
public class VoteServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(VoteServiceApp.class, args);
    }
}
