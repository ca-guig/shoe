package ca.guig.shoe.service;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UuidGenerator implements IdGenerator {

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }
}
