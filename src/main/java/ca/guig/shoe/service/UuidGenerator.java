package ca.guig.shoe.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidGenerator implements IdGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
