package ca.guig.shoe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

class AbstractInMemoryCrudRepositoryTest {

    private final AbstractInMemoryCrudRepository<TestEntity> repository = new AbstractInMemoryCrudRepository<>() {};

    @Test
    void saveShouldSaveEntityWhenEntityDoesNotExist() {
        TestEntity entity = entity("123456", "test-entity-name");
        TestEntity returnedEntity = repository.save(entity);

        TestEntity expectedEntity = entity("123456", "test-entity-name");
        assertThat(returnedEntity).isEqualTo(expectedEntity);
        assertThat(repository.read("123456")).isEqualTo(expectedEntity);
    }

    @Test
    void saveShouldReplaceEntityWhenEntityExists() {
        given(entity("1000", "test-entity-name"));

        TestEntity returnedEntity = repository.save(entity("1000", "new-test-entity-name"));

        TestEntity expectedEntity = entity("1000", "new-test-entity-name");
        assertThat(returnedEntity).isEqualTo(expectedEntity);
        assertThat(repository.read("1000")).isEqualTo(expectedEntity);
    }

    @Test
    void saveShouldCopyEntityObject() {
        TestEntity entity = entity("123456", "test-entity-name");
        TestEntity returnedEntity = repository.save(entity);

        TestEntity expectedEntity = entity("123456", "test-entity-name");
        assertThat(returnedEntity).isEqualTo(expectedEntity);
        assertThat(repository.read("123456")).isEqualTo(expectedEntity);
    }

    @Test
    void readShouldReturnEntityWhenEntityExist() {
        given(entity("1000", "test-entity-name"));

        TestEntity returnedEntity = repository.read("1000");
        assertThat(returnedEntity).isEqualTo(entity("1000", "test-entity-name"));
    }

    @Test
    void deleteShouldDeleteEntity() {
        given(entity("1000", "test-entity-name"));

        repository.delete("1000");

        assertThat(repository.read("1000")).isNull();
    }

    @Test
    void findAllShouldReturnAllEntitys() {
        given(
                entity("1000", "entity-a"),
                entity("2000", "entity-b"));

        List<TestEntity> entitys = repository.findAll();

        assertThat(entitys).containsExactlyInAnyOrder(entity("1000", "entity-a"), entity("2000", "entity-b"));
    }

    private void given(TestEntity... entities) {
        for (TestEntity entity : entities) {
            repository.save(entity);
        }
    }

    private static TestEntity entity(String id, String name) {
        return new TestEntity(id, name);
    }

    public static class TestEntity implements Identifiable {
        private String id;
        private String name;

        TestEntity(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            TestEntity that = (TestEntity) o;
            return Objects.equals(id, that.id)
                    && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "TestEntity{"
                    + "id='" + id + '\''
                    + ", name='" + name + '\''
                    + '}';
        }
    }
}