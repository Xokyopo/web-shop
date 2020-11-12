package ru.geekbrains.coursework.webshop.app.external.pages.bootadmin.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.geekbrains.coursework.webshop.app.dao.ARepository;
import ru.geekbrains.coursework.webshop.app.domain.AService;
import ru.geekbrains.coursework.webshop.app.external.exceptions.EntityNotFoundException;
import ru.geekbrains.coursework.webshop.app.utils.ProgramUtils;

public abstract class AController<E, R extends AService<E, ? extends ARepository<E>>> {
    private String entityName;
    private String rootPath;
    private String entitiesTablePath;
    private String editFormPath;
    private R service;

    @Autowired
    public void init(R service) {
        rootPath = ProgramUtils.getRequestMappingValue(this).stream()
                .map(ProgramUtils::addSlashOnStartAndRemoveOnEnd)
                .findFirst()
                .orElse("");
        this.service = service;
        this.entityName = service.getEntityName().toLowerCase();
        this.entitiesTablePath = rootPath + "-list";
        this.editFormPath = rootPath + "-edit-form";
    }

    @GetMapping("/showAll")
    public String showAll(Model model) {
        model.addAttribute(this.entityName + "s", this.service.getAll());
        return this.entitiesTablePath;
    }

    @GetMapping("/show/{id}")
    public String show(Model model, @PathVariable("id") Long id) {
        model.addAttribute(this.entityName, this.service.getById(id).orElseThrow(() -> new EntityNotFoundException("Entity with id [" + id + "not found")));
        return this.editFormPath;
    }

    @PostMapping("/del/{id}")
    public String delete(@PathVariable("id") Long id) {
        this.service.delete(id);
        return "redirect:" + this.rootPath + "/showAll";
    }

    @PostMapping("/save")
    public String save(E entity) {
        this.service.save(entity);
        return "redirect:" + this.rootPath + "/showAll";
    }

    public R getService() {
        return service;
    }

    protected String getRootPath() {
        return rootPath;
    }

    protected String getEntitiesTablePath() {
        return entitiesTablePath;
    }

    protected String getEditFormPath() {
        return editFormPath;
    }
}
