package com.oc.projet7api.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.oc.projet7api.model.entity.Book;

public class BookSpecifications {
	public static Specification<Book> hasKeywords(List<String> keywords) {
		return (root, query, criteriaBuilder) -> {
			if (keywords == null || keywords.isEmpty()) {
				return criteriaBuilder.conjunction();
			}
			
			return keywords.stream()
					.map(keyword -> {
						String likePattern = "%" + keyword.toLowerCase() + "%";
						
						return criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), likePattern),
								criteriaBuilder.like(criteriaBuilder.lower(root.get("author")), likePattern));
					})
					.reduce(criteriaBuilder::and)
					.orElse(criteriaBuilder.conjunction());
		};
	}
}
