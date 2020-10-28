package ca.guig.shoe;

import org.springframework.boot.SpringApplication;

public final class Launcher {

    private Launcher() {}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
