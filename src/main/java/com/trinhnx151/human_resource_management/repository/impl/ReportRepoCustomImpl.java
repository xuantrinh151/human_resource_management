package com.trinhnx151.human_resource_management.repository.impl;

import com.trinhnx151.human_resource_management.dto.sdi.department.DepartmentReportSdi;
import com.trinhnx151.human_resource_management.dto.sdi.employee.EmployeeReportSdi;
import com.trinhnx151.human_resource_management.dto.sdo.department.DepartmentReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.BestEmployeeReportSdo;
import com.trinhnx151.human_resource_management.dto.sdo.employee.EmployeeReportSdo;
import com.trinhnx151.human_resource_management.repository.ReportRepoCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class ReportRepoCustomImpl implements ReportRepoCustom {

    private final EntityManager em;

    @Override
    public Page<DepartmentReportSdo> report(DepartmentReportSdi request, Pageable pageable) {
        String from = request.getFrom();
        String to = request.getTo();
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);
        Map<String, Object> queryParams = new HashMap<>();
        String sqlCountAll = "SELECT COUNT(1) ";
        String sqlGetData = "SELECT " +
                "D.CODE, " +
                "D.NAME, " +
                "SUM(R.TYPE = TRUE) as'TT', "+
                "SUM(R.TYPE = FALSE) as 'KL', " +
                "(SUM(R.TYPE = TRUE)  - SUM(R.TYPE = FALSE) ), ";
        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM RECORD R INNER JOIN EMPLOYEE E ON R.EMPLOYEE_ID = E.ID INNER JOIN DEPARTMENT D ON D.ID = E.DEPARTMENT_ID ");
        sqlConditional.append("WHERE E.STATUS <> 2 ");
        if (from != null && to != null) {
            sqlConditional.append("AND (R.CREATE_TIME BETWEEN :from AND :to) ");
            queryParams.put("from",from);
            queryParams.put("to", to);
        }
        sqlConditional.append(" GROUP BY D.CODE,D.NAME ");

        Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);
        List<Object[]> queryResult = query.getResultList();
        List<DepartmentReportSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            DepartmentReportSdo departmentReportSdo = new DepartmentReportSdo();
            departmentReportSdo.setCodeDepartment(((String) object[0]));
            departmentReportSdo.setNameDepartment((String) object [1]);
            departmentReportSdo.setTotalAchievement(((BigInteger) object[2]).intValue());
            departmentReportSdo.setTotalDiscipline(((BigInteger) object[3]).intValue());
            departmentReportSdo.setTotalPoint(((BigInteger) object[4]).intValue());
            listData.add(departmentReportSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        int countAll = queryCountAll.getFirstResult();
        return new PageImpl<>(listData, pageable, countAll);
    }

    @Override
    public Page<EmployeeReportSdo> report(EmployeeReportSdi request, Pageable pageable) {
        String from = request.getFrom();
        String to = request.getTo();
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);
        Map<String, Object> queryParams = new HashMap<>();

        String sqlCountAll = "SELECT COUNT(1) ";

        String sqlGetData = "SELECT " +
                "E.CODE, " +
                "E.NAME, " +
                "SUM(R.TYPE = TRUE), "+
                "SUM(R.TYPE = FALSE), " +
                "(SUM(R.TYPE = TRUE)  - SUM(R.TYPE = FALSE) ), ";

        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM RECORD R INNER JOIN EMPLOYEE E ON R.EMPLOYEE_ID = E.ID ");
        sqlConditional.append("WHERE E.STATUS <> 2 ");
        if (from != null && to != null) {
            sqlConditional.append("AND (R.CREATE_TIME BETWEEN :from AND :to)");
            queryParams.put("from",from);
            queryParams.put("to", to);
        }
        sqlConditional.append("GROUP BY E.CODE,E.NAME ");
        Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);
        List<Object[]> queryResult = query.getResultList();
        List<EmployeeReportSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            EmployeeReportSdo employeeReportSdo = new EmployeeReportSdo();
            employeeReportSdo.setCodeEmployee(((String) object[0]));
            employeeReportSdo.setNameEmployee((String) object [1]);
            employeeReportSdo.setTotalAchievement(((BigInteger) object[2]).intValue());
            employeeReportSdo.setTotalDiscipline(((BigInteger) object[3]).intValue());
            employeeReportSdo.setTotalPoint(((BigInteger) object[4]).intValue());
            listData.add(employeeReportSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        int countAll = queryCountAll.getFirstResult();
        return new PageImpl<>(listData, pageable, countAll);
    }

    @Override
    public Page<BestEmployeeReportSdo> reportBestEmployee(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int firstResult = pageSize * (pageable.getPageNumber()-1);
        Map<String, Object> queryParams = new HashMap<>();
        String sqlCountAll = "SELECT COUNT(1) ";
        String sqlGetData = "SELECT " +
                "E.CODE, " +
                "E.NAME, " +
                "E.IMAGE, "+
                "(SUM(R.TYPE = TRUE)  - SUM(R.TYPE = FALSE) ) ";
        StringBuilder sqlConditional = new StringBuilder();
        sqlConditional.append("FROM RECORD R INNER JOIN EMPLOYEE E ON R.EMPLOYEE_ID = E.ID ");
        sqlConditional.append("WHERE E.STATUS <> 2 ");
        sqlConditional.append("GROUP BY E.CODE,E.NAME,E.IMAGE ");
        sqlConditional.append("ORDER BY (SUM(R.TYPE = TRUE) - SUM(R.TYPE =FALSE)) DESC ");
        Query query = em.createNativeQuery(sqlGetData + sqlConditional);
        query.setMaxResults(pageSize);
        query.setFirstResult(firstResult);
        queryParams.forEach(query::setParameter);
        List<Object[]> queryResult = query.getResultList();
        List<BestEmployeeReportSdo> listData = new ArrayList<>();
        for (Object[] object : queryResult) {
            BestEmployeeReportSdo bestEmployeeReportSdo = new BestEmployeeReportSdo();
            bestEmployeeReportSdo.setCodeEmployee(((String) object[0]));
            bestEmployeeReportSdo.setNameEmployee((String) object [1]);
            bestEmployeeReportSdo.setImage((String) object [2]);
            bestEmployeeReportSdo.setTotalPoint(((BigInteger) object[3]).intValue());
            listData.add(bestEmployeeReportSdo);
        }
        Query queryCountAll = em.createNativeQuery(sqlCountAll + sqlConditional);
        int countAll = queryCountAll.getFirstResult();

        return new PageImpl<>(listData, pageable, countAll);
    }
}
