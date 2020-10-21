package ru.geekbrains.coursework.webshop.app.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;

import java.util.List;
import java.util.Optional;

public abstract class AService<E, R extends ARepository<E>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final boolean debug = true;
    private R repository;
    private Class<E> entitiesClass;

    public AService() {
    }

    @Autowired
    public void setRepository(R repository) {
        this.repository = repository;
    }

    public List<E> getAll() {
        this.getLogger().ifPresent((myLogger) -> {
            myLogger.debug("method name {public List<E> getAll}");
        });
        return this.repository.findAll();
    }

    public Page<E> getAll(Pageable pageable) {
        this.getLogger().ifPresent((myLogger) -> {
            myLogger.debug("run method name {public Page<E> getAll(Pageable pageable)} where pageable is [" + pageable + "]");
        });
        this.requireNotNull(pageable, "pageable cant be NULL");
        return this.repository.findAll(pageable);
    }

    public Optional<E> getById(Long id) {
        this.getLogger().ifPresent((myLogger) -> {
            myLogger.debug("run method name {public Optional<E> getById} id is [" + id + "]");
        });
        this.requireNotNull(id, "EntityID Cant be NULL");
        return this.repository.findById(id);
    }

    public void save(E entity) {
        this.getLogger().ifPresent((myLogger) -> {
            myLogger.debug("run method name {public void add(E entity)} entity is [" + entity + "]");
        });
        this.requireNotNull(entity, "Entity Cant be NULL");
        this.repository.save(entity);
    }

    public void delete(Long id) {
        this.getLogger().ifPresent((myLogger) -> {
            myLogger.debug("run method name {public void del(Long id)} id is [" + id + "]");
        });
        this.requireNotNull(id, "Entity Cant be NULL");
        this.repository.deleteById(id);
    }

    protected void requireNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    protected Optional<Logger> getLogger() {
        return this.debug ? Optional.empty() : Optional.of(this.logger);
    }
}
