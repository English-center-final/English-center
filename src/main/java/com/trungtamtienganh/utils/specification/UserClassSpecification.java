package com.trungtamtienganh.utils.specification;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Level;
import com.trungtamtienganh.entity.User;
import com.trungtamtienganh.entity.UserClasses;

public final class UserClassSpecification {

	public static Specification<UserClasses> findByUserName(String name) {

		return (root, query, criteriaBuilder) -> {

			Join<UserClasses, User> classesJoin = root.join("user");
			Expression<String> exp1 = classesJoin.get("name");

			return name.equals("") ? null : criteriaBuilder.like(exp1, "%" + name + "%");

		};
	}

	public static Specification<UserClasses> findByClassesId(Integer classesId) {

		return (root, query, criteriaBuilder) -> {

			Join<UserClasses, Classes> classesJoin = root.join("classes");
			Expression<String> exp1 = classesJoin.get("id");

			return classesId == null ? null : criteriaBuilder.equal(exp1, classesId);

		};
	}

	public static Specification<UserClasses> findByLevelSlug(String levelSlug) {

		return (root, query, criteriaBuilder) -> {

			Join<UserClasses, Classes> classesJoin = root.join("classes");
			Join<Classes, Level> levelJoin = classesJoin.join("level");
			Expression<String> exp1 = levelJoin.get("slug");

			return levelSlug.equals("") ? null : criteriaBuilder.equal(exp1, levelSlug);

		};
	}

	public static Specification<UserClasses> findByStatus(String status) {

		return (root, query, criteriaBuilder) -> {

			return status.equals("") ? null : criteriaBuilder.equal(root.get("status"), status);

		};

	}

}
