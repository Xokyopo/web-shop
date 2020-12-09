package ru.geekbrains.coursework.webshop.app.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.geekbrains.coursework.webshop.app.CallCurrentMethodException;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AServiceTest {
    private TestEntityRepository testEntityRepository;
    private TestEntityService testEntityService;

    @BeforeEach
    public void init() {
        this.testEntityRepository = Mockito.mock(TestEntityRepository.class);
        this.testEntityService = new TestEntityService();
        this.testEntityService.init(this.testEntityRepository);
    }

    @Test
    public void getEntityName_Should_Return_True() {
        String expected = TestEntity.class.getSimpleName();
        String actual = this.testEntityService.getEntityName();
        assertEquals(expected, actual);
    }

    @Test
    public void getRepository_Should_Return_True() {
        TestEntityRepository expected = this.testEntityRepository;
        TestEntityRepository actual = this.testEntityService.getRepository();
        assertEquals(expected, actual);
    }

    @Test
    public void delete_ShouldThrowException_WhenInputNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.testEntityService.delete(null);
        assertThrows(expected, actual);
    }

    @Test
    public void getPage_ShouldThrowException_WhenInputNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.testEntityService.getPage(null);
        assertThrows(expected, actual);
    }

    @Test
    public void getById_ShouldThrowException_WhenInputNull() {
        Class<IllegalArgumentException> expected = IllegalArgumentException.class;
        Executable actual = () -> this.testEntityService.getById(null);
        assertThrows(expected, actual);
    }

    @Test
    public void getById_ShouldGetEmptyTestEntity_WhenInputZero() {
        TestEntity expected = new TestEntity();
        TestEntity actual = this.testEntityService.getById(0L).get();
        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldGetEmptyTestEntity_WhenInputMaxLongValue() {
        TestEntity expected = new TestEntity();
        Mockito.when(this.testEntityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        TestEntity actual = this.testEntityService.getById(Long.MAX_VALUE).get();
        assertEquals(expected, actual);
    }

    @Test
    public void getById_ShouldGetEmptyTestEntity_WhenInputMinLongValue() {
        TestEntity expected = new TestEntity();
        Mockito.when(this.testEntityRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expected));
        TestEntity actual = this.testEntityService.getById(Long.MIN_VALUE).get();
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_Should_Return_True() {
        Class<CallCurrentMethodException> expected = CallCurrentMethodException.class;
        Mockito.when(this.testEntityRepository.findAll()).thenThrow(new CallCurrentMethodException("getAll_Should_Return_True"));
        Executable actual = () -> this.testEntityService.getAll();
        assertThrows(expected, actual);
    }

    @Test
    public void getPage_Should_Return_True() {
        Class<CallCurrentMethodException> expected = CallCurrentMethodException.class;
        Mockito.when(this.testEntityRepository.findAll(Mockito.any(Pageable.class))).thenThrow(new CallCurrentMethodException("getPage_Should_Return_True"));
        Executable actual = () -> this.testEntityService.getPage(PageRequest.of(1, 1));
        assertThrows(expected, actual);
    }

    private interface TestEntityRepository extends ARepository<TestEntity> {
    }

    private static class TestEntityService extends AService<TestEntity, TestEntityRepository> {
    }

    private static class TestEntity {
        private long id;
        private String name;

        public TestEntity() {
        }

        public TestEntity(long id, String name) {
            this.id = id;
            this.name = name;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestEntity that = (TestEntity) o;
            return getId() == that.getId() && Objects.equals(getName(), that.getName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(), getName());
        }
    }
}
