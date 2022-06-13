package com.trungtamtienganh.utils.specification;

import java.time.LocalDate;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.trungtamtienganh.entity.Branch;
import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Level;
import com.trungtamtienganh.utils.DateProcessor;

public final class ClassesSpecification {

	public static Specification<Classes> findByLevelSlug(String levelSlug) {

		return (root, query, criteriaBuilder) -> {

			Join<Classes, Level> routeJoin = root.join("level");
			Expression<String> exp1 = routeJoin.get("slug");

			return levelSlug.equals("") ? null : criteriaBuilder.like(exp1, "%" + levelSlug + "%");

		};
	}

	public static Specification<Classes> findByBranch(Integer branchId) {

		if (branchId == null || branchId < 0)
			return null;
		System.out.println("branchId: " + branchId);

		return (root, query, criteriaBuilder) -> {

			Join<Classes, Branch> branchJoin = root.join("branch");
			Expression<String> exp1 = branchJoin.get("id");

			return criteriaBuilder.equal(exp1, branchId);

		};
	}

	public static Specification<Classes> findByStatus(String status) {

		return (root, query, criteriaBuilder) -> {

			return status.equals("") ? null : criteriaBuilder.like(root.get("status"), status);

		};
	}

	public static Specification<Classes> findByDateStart(String dateStart) {

		if (dateStart.equals("")) {
			return null;
		}

		LocalDate date = DateProcessor.toLocalDate(dateStart);

		return (root, query, criteriaBuilder) -> {

			return criteriaBuilder.greaterThanOrEqualTo(root.get("dateStart"), date);

		};
	}

	public static Specification<Classes> findByDateEnd(String dateEnd) {

		if (dateEnd.equals("")) {
			return null;
		}

		LocalDate date = DateProcessor.toLocalDate(dateEnd);

		return (root, query, criteriaBuilder) -> {

			return criteriaBuilder.lessThanOrEqualTo(root.get("dateStart"), date);

		};

	}

}
