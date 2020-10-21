package ru.geekbrains.coursework.webshop.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface ARepository<E> extends JpaRepository<E, Long>, PagingAndSortingRepository<E, Long> {
}
