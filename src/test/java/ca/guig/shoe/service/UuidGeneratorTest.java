package ca.guig.shoe.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class UuidGeneratorTest {

    private final IdGenerator idGenerator = new UuidGenerator();

    @Test
    void generateIdShouldReturnNewIdEachTime() {
        String id1 = idGenerator.generateId();
        String id2 = idGenerator.generateId();

        assertThat(id1).isNotEqualTo(id2);
    }
}
