
package com.click2vote.auth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication(scanBasePackages = {"com.click2vote"})
public class AuthServiceApp {
    public static void main(String[] args){ SpringApplication.run(AuthServiceApp.class, args); }
}
