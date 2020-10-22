package ru.geekbrains.coursework.webshop.app.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;

import java.util.List;
import java.util.Optional;

public abstract class AService<E, R extends ARepository<E>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean debug;
    private Class<E> entitiesClass;
    private R repository;

    @Autowired
    public void init(R repository, Environment environment) {
        this.debug = Optional.ofNullable(environment.getProperty("mylogger.debug", boolean.class)).orElse(false);
        this.getLogger().ifPresent((myLogger) -> myLogger.info("run method name {public void init(R repository, Environment environment)}"));

        this.repository = repository;
        this.entitiesClass = this.getEntitiesClassBy(repository);
    }

    public List<E> getAll() {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public List<E> getAll}"));

        return this.repository.findAll();
    }

    public Page<E> getAll(Pageable pageable) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public Page<E> getAll(Pageable pageable)} where pageable is [" + pageable + "]"));
        this.requireNotNull(pageable, "pageable cant be NULL");

        return this.repository.findAll(pageable);
    }

    public Optional<E> getById(Long id) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public Optional<E> getById} id is [" + id + "]"));
        this.requireNotNull(id, "EntityID Cant be NULL");

        return (id == 0) ? Optional.of(this.getEmptyEntity()) : this.repository.findById(id);
    }

    public void save(E entity) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public void add(E entity)} entity is [" + entity + "]"));
        this.requireNotNull(entity, "Entity Cant be NULL");

        this.repository.save(entity);
    }

    public void delete(Long id) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public void del(Long id)} id is [" + id + "]"));
        this.requireNotNull(id, "Entity Cant be NULL");

        this.repository.deleteById(id);
    }

    public String getEntityName() {
        return this.entitiesClass.getSimpleName();
    }

    protected void requireNotNull(Object object, String message) {
        if (object == null) {
            this.getLogger().ifPresent(myLogger -> myLogger.error(message));
            throw new IllegalArgumentException(message);
        }
    }

    protected Optional<Logger> getLogger() {
        return debug ? Optional.of(logger) : Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private Class<E> getEntitiesClassBy(R repository) {
        ResolvableType resolvableType = ResolvableType.forClass(repository.getClass()).as(JpaRepository.class);
        return (Class<E>) resolvableType.getGeneric(0).toClass();
    }

    private E getEmptyEntity() {
        try {
            return this.entitiesClass.newInstance();
        } catch (Exception e) {
            this.getLogger().ifPresent(myLogger -> myLogger.error(e.getMessage()));
            throw new RuntimeException(e);
        }
    }
}
