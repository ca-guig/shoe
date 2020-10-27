package ca.guig.shoe.domain;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Game {

    @JsonProperty(access = READ_ONLY)
    private final String id;

    private final String name;

    public Game(String name) {
        this(null, name);
    }

    public Game(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
