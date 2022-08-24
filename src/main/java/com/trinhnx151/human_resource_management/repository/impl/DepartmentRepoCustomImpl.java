package com.trinhnx151.human_resource_management.repository.impl;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentSearchSdo;
import com.trinhnx151.human_resource_management.repository.DepartmentRepoCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class DepartmentRepoCustomImpl implements DepartmentRepoCustom {
    private final EntityManager em;

    @Override
    public Page<DepartmentSearchSdo> search(DepartmentSearchSdi request, Pageable pageable) {
        String keyword = request.getKeyword().toUpperCase();
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);

        Map<String, Object> queryParams = new HashMap<>();

        String sqlCountAll = "SELECT COUNT(1) ";
        String sqlGetData = "SELECT " +
                "D.ID, " +
                "D.CODE, " +
                "D.NAME, " +
                "D.STATUS ";
        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM DEPARTMENT D ");
        sqlConditional.append("WHERE D.STATUS <> 2 ");
        if (keyword != null && !keyword.isEmpty()) {
            sqlConditional.append("AND ( UPPER(D.CODE) LIKE :keyword OR UPPER(D.NAME) LIKE :keyword) ");
            queryParams.put("keyword", "%" + keyword + "%");
        }
        Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);

        List<Object[]> queryResult = query.getResultList();
        List<DepartmentSearchSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            DepartmentSearchSdo departmentSearchSdo = new DepartmentSearchSdo();
            departmentSearchSdo.setId(((BigInteger) object[0]).longValue());
            departmentSearchSdo.setCode((String) object [1]);
            departmentSearchSdo.setName((String) object[2]);
            departmentSearchSdo.setStatus((Integer) object[3]);
            listData.add(departmentSearchSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        int countAll = queryCountAll.getFirstResult();
        return new PageImpl<>(listData, pageable, countAll);
    }
}
