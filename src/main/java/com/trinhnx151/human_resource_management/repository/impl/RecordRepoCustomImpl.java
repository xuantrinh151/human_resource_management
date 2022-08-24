package com.trinhnx151.human_resource_management.repository.impl;

import com.trinhnx151.human_resource_management.dto.sdi.record.RecordSearchSdi;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeSearchSdo;
import com.trinhnx151.human_resource_management.dto.sdo.record.RecordSearchSdo;
import com.trinhnx151.human_resource_management.repository.RecordRepoCustom;
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
public class RecordRepoCustomImpl implements RecordRepoCustom {

    private final EntityManager em;

    @Override
    public Page<RecordSearchSdo> search(RecordSearchSdi request, Pageable pageable) {
        String keyword = request.getName();
        boolean type = request.isType();
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);
        Map<String, Object> queryParams = new HashMap<>();
        String sqlCountAll = "SELECT COUNT(1) ";
        String sqlGetData = "SELECT " +
                "R.ID, " +
                "E.CODE, " +
                "E.NAME, " +
                "R.REASON, "+
                "R.STATUS ";
        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM RECORD R INNER JOIN EMPLOYEE E ON R.EMPLOYEE_ID = E.ID ");
        sqlConditional.append("WHERE R.STATUS <> 2 ");

        if (keyword != null && !keyword.isEmpty()) {
            sqlConditional.append("AND (E.NAME LIKE :keyword AND R.TYPE = :type) ");

            queryParams.put("keyword", "%" + keyword + "%");
            queryParams.put("type", type);
        }
        Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);
        List<Object[]> queryResult = query.getResultList();
        List<RecordSearchSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            RecordSearchSdo recordSearchSdo = new RecordSearchSdo();
            recordSearchSdo.setId(((BigInteger) object[0]).longValue());
            recordSearchSdo.setCodeEmployee((String) object [1]);
            recordSearchSdo.setNameEmployee((String) object[2]);
            recordSearchSdo.setReason((String) object[3]);
            recordSearchSdo.setStatus((Integer) object [4]);
            listData.add(recordSearchSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        int countAll = queryCountAll.getFirstResult();
        return new PageImpl<>(listData, pageable, countAll);
    }
}
