package com.trungtamtienganh.utils.specification;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.trungtamtienganh.entity.Classes;
import com.trungtamtienganh.entity.Schedule;
import com.trungtamtienganh.utils.DateProcessor;

public final class ScheduleSpecification {

	public static Specification<Schedule> findByClassesId(Integer classesId) {

		return (root, query, criteriaBuilder) -> {

			Join<Schedule, Classes> classesJoin = root.join("classes");
			Expression<String> exp1 = classesJoin.get("id");

			return criteriaBuilder.equal(exp1, classesId);

		};
	}

	public static Specification<Schedule> findByClassesId(List<Integer> classesIds) {

		return (root, query, criteriaBuilder) -> {

			if (classesIds == null || classesIds.size() == 0) {
				return null;
			}

			Join<Schedule, Classes> classesJoin = root.join("classes");

			return classesJoin.get("id").in(classesIds);

		};
	}

	public static Specification<Schedule> findByDateStart(String dateStart) {

		if (dateStart.equals("")) {
			return null;
		}

		LocalDate date = DateProcessor.toLocalDate(dateStart);

		return (root, query, criteriaBuilder) -> {

			return criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date);

		};
	}

	public static Specification<Schedule> findByDateEnd(String dateEnd) {

		if (dateEnd.equals("")) {
			return null;
		}

		LocalDate date = DateProcessor.toLocalDate(dateEnd);

		return (root, query, criteriaBuilder) -> {

			return criteriaBuilder.lessThanOrEqualTo(root.get("date"), date);

		};

	}

}
