package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;

import java.util.List;
import java.util.Optional;

public abstract class AService<E, R extends ARepository<E>> {
    private Class<E> entitiesClass;
    private R repository;

    @Autowired
    public void init(R repository) {
        this.repository = repository;
        this.entitiesClass = this.getEntitiesClassBy(repository);
    }

    public List<E> getAll() {
        return this.repository.findAll();
    }

    public Page<E> getPage(Pageable pageable) {
        this.requireNotNull(pageable, "Pageable Can't be NULL");

        return this.repository.findAll(pageable);
    }

    public Optional<E> getById(Long id) {
        this.requireNotNull(id, "EntityID Can't be NULL");

        return (id == 0) ? Optional.of(this.getEmptyEntity()) : this.repository.findById(id);
    }

    public void save(E entity) {
        this.requireNotNull(entity, "Entity Can't be NULL");

        this.repository.save(entity);
    }

    public void delete(Long id) {
        this.requireNotNull(id, "Entity id Can't be NULL");

        this.repository.deleteById(id);
    }

    public R getRepository() {
        return repository;
    }

    public String getEntityName() {
        return this.entitiesClass.getSimpleName();
    }

    protected void requireNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntitiesClassBy(R repository) {
        ResolvableType resolvableType = ResolvableType.forClass(repository.getClass()).as(JpaRepository.class);
        return (Class<E>) resolvableType.getGeneric(0).toClass();
    }

    protected final E getEmptyEntity() {
        try {
            return this.entitiesClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
