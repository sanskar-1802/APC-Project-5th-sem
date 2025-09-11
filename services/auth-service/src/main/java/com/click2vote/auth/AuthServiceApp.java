
// package com.click2vote.auth;
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// @SpringBootApplication(scanBasePackages = {"com.click2vote"})
// public class AuthServiceApp {
//     public static void main(String[] args){ SpringApplication.run(AuthServiceApp.class, args); }
// }

package com.click2vote.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.click2vote"})
@EntityScan(basePackages = {"com.click2vote.common.domain"})  // 👉 Your User entity package
@EnableJpaRepositories(basePackages = {"com.click2vote.auth.repo"})  // 👉 Your repository package
public class AuthServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApp.class, args);
    }
}
