package com.trinhnx151.human_resource_management.repository.impl;

import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.repository.EmployeeRepoCustom;
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
public class EmployeeRepoCustomImpl implements EmployeeRepoCustom {

    private final EntityManager em;

    @Override
    public Page<EmployeeSearchSdo> search(EmployeeSearchSdi request, Pageable pageable) {
        String keyword = request.getKeyword();
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);
        Map<String, Object> queryParams = new HashMap<>();
        String sqlCountAll = "SELECT COUNT(1) ";
        String sqlGetData = "SELECT " +
                "E.ID, " +
                "E.CODE, " +
                "E.NAME, " +
                "E.GENDER, "+
                "E.EMAIL, " +
                "E.PHONE, "+
                "E.STATUS ";

        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM EMPLOYEE E ");
        sqlConditional.append("WHERE E.STATUS <> 2 ");
        if (keyword != null && !keyword.isEmpty()) {
            sqlConditional.append("AND (E.CODE LIKE :keyword OR E.NAME LIKE :keyword OR E.PHONE LIKE :keyword OR E.EMAIL LIKE :keyword OR E.GENDER LIKE :keyword OR E.LEVEL LIKE :keyword) ");

            queryParams.put("keyword", "%" + keyword + "%");
        }

        javax.persistence.Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);
        List<Object[]> queryResult = query.getResultList();
        List<EmployeeSearchSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            EmployeeSearchSdo employeeSearchSdo = new EmployeeSearchSdo();
            employeeSearchSdo.setId(((BigInteger) object[0]).longValue());
            employeeSearchSdo.setCode((String) object [1]);
            employeeSearchSdo.setFullName((String) object[2]);
            employeeSearchSdo.setGender((Boolean) object[3]);
            employeeSearchSdo.setEmail((String) object [4]);
            employeeSearchSdo.setPhone((String) object[5]);
            employeeSearchSdo.setStatus((Integer) object[6]);
            listData.add(employeeSearchSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        Integer countAll = queryCountAll.getFirstResult();
        return new PageImpl<>(listData, pageable, countAll.longValue());
    }
}
