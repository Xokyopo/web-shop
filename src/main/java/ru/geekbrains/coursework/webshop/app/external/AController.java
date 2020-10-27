package ru.geekbrains.coursework.webshop.app.external;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;
import ru.geekbrains.coursework.webshop.app.domain.AService;
import ru.geekbrains.coursework.webshop.app.external.exceptions.EntityNotFoundException;

import java.util.Optional;

public abstract class AController<E, R extends AService<E, ? extends ARepository<E>>> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private boolean debug;
    private String entityName;
    private String entitiesTablePath;
    private String editFormPath;
    private R service;

    @Autowired
    public void init(R service, Environment environment) {
        this.debug = Optional.ofNullable(environment.getProperty("mylogger.debug", boolean.class)).orElse(false);
        this.getLogger().ifPresent((myLogger) -> myLogger.info("run method name {public void init(R service, Environment environment)}"));
        this.service = service;
        this.entityName = service.getEntityName().toLowerCase();
        this.entitiesTablePath = "/" + entityName + "-list";
        this.editFormPath = "/" + entityName + "-edit-form";
    }

    @GetMapping
    public String showAll(Model model) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public String showAll(Model model)}"));
        model.addAttribute(this.entityName + "s", this.service.getAll());
        return this.entitiesTablePath;
    }

    @GetMapping({"/show/{id}"})
    public String show(Model model, @PathVariable("id") Long id) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public String show(Model model, @PathVariable('id') Long id)} where id is [" + id + "]"));
        model.addAttribute(this.entityName, this.service.getById(id).orElseThrow(() -> new EntityNotFoundException("Entity with id [" + id + "not found")));
        return this.editFormPath;
    }

    @PostMapping({"/del/{id}"})
    public String delete(@PathVariable("id") Long id) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public String delete(Model model, @PathVariable('id') Long id)} where id is [" + id + "]"));
        this.service.delete(id);
        //TODO нужно делать редирект на корень котроллера а не на файл
        return "redirect:" + this.entitiesTablePath;
    }

    @PostMapping({"/save"})
    public String save(E entity) {
        this.getLogger().ifPresent((myLogger) -> myLogger.debug("run method name {public String save(E entity)} where entity is [" + entity + "]"));
        this.service.save(entity);
        //TODO нужно делать редирект на корень котроллера а не на файл
        return "redirect:" + this.entitiesTablePath;
    }

    protected Optional<Logger> getLogger() {
        return debug ? Optional.of(logger) : Optional.empty();
    }
}
