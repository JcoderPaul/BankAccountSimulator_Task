package prod.oldboy.repository.user_repo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import prod.oldboy.database.entity.User;
import prod.oldboy.dto.user_dto.UserSearchCriteria;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FilterUserRepositoryImpl implements FilterUserRepository{

    private final EntityManager entityManager;

    @Override
    public Page<User> findAllWithFilter(UserSearchCriteria criteriaFilter, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot);

        List<Predicate> predicates = getPredicates(criteriaFilter, criteriaBuilder, userRoot);

        userCriteriaQuery.where(predicates.toArray(value -> new Predicate[value]));

        List<User> filteredQueryResult = entityManager.createQuery(userCriteriaQuery).getResultList();

        TypedQuery<User> typedQuery = entityManager.createQuery(userCriteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        long userCount = filteredQueryResult.size();

        return new PageImpl<>(typedQuery.getResultList(), pageable, userCount);
    }

    private static List<Predicate> getPredicates(UserSearchCriteria criteriaFilter,
                                                 CriteriaBuilder criteriaBuilder,
                                                 Root<User> userRoot) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteriaFilter.firstname() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("firstname"),
                    "%" + criteriaFilter.firstname() + "%"));
        }
        if (criteriaFilter.lastname() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("lastname"),
                    "%" + criteriaFilter.lastname() + "%"));
        }
        if (criteriaFilter.patronymic() != null) {
            predicates.add(criteriaBuilder.like(userRoot.get("patronymic"),
                    "%" + criteriaFilter.patronymic() + "%"));
        }
        if (criteriaFilter.birthDate() != null) {
            predicates.add(criteriaBuilder.greaterThan(userRoot.get("birthDate"),
                    criteriaFilter.birthDate()));
        }
        return predicates;
    }

    @Override
    public Page<User> findUserByBirthDayPageable(LocalDate date, Pageable pageable){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> userCriteriaQuery = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = userCriteriaQuery.from(User.class);
        userCriteriaQuery.select(userRoot);

        userCriteriaQuery.where(criteriaBuilder.greaterThan(userRoot.get("birthDate"), date));

        List<User> filteredQueryResult = entityManager.createQuery(userCriteriaQuery).getResultList();

        TypedQuery<User> typedQuery = entityManager.createQuery(userCriteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        long userCount = filteredQueryResult.size();

        return new PageImpl<>(typedQuery.getResultList(), pageable, userCount);
    };
}
