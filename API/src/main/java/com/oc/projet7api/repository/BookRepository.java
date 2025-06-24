package com.oc.projet7api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.oc.projet7api.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
	Optional<Book> findById(Long id);
	
	List<Book> findAll(Specification<Book> spec);
	
	List<Book> findTop5ByOrderByIdDesc();
}
