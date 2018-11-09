/*Copyright (c) 2016-2017 vcstest4.com All Rights Reserved.
 This software is the confidential and proprietary information of vcstest4.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vcstest4.com*/
package com.testallservicesforvcs.adventureworks2014.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.testallservicesforvcs.adventureworks2014.Employee;
import com.testallservicesforvcs.adventureworks2014.EmployeeDepartmentHistory;
import com.testallservicesforvcs.adventureworks2014.EmployeePayHistory;
import com.testallservicesforvcs.adventureworks2014.JobCandidate;


/**
 * ServiceImpl object for domain model class Employee.
 *
 * @see Employee
 */
@Service("AdventureWorks2014.EmployeeService")
@Validated
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Lazy
    @Autowired
    @Qualifier("AdventureWorks2014.EmployeePayHistoryService")
    private EmployeePayHistoryService employeePayHistoryService;

    @Lazy
    @Autowired
    @Qualifier("AdventureWorks2014.JobCandidateService")
    private JobCandidateService jobCandidateService;

    @Lazy
    @Autowired
    @Qualifier("AdventureWorks2014.EmployeeDepartmentHistoryService")
    private EmployeeDepartmentHistoryService employeeDepartmentHistoryService;

    @Autowired
    @Qualifier("AdventureWorks2014.EmployeeDao")
    private WMGenericDao<Employee, Integer> wmGenericDao;

    @Autowired
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<Employee, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee create(Employee employee) {
        LOGGER.debug("Creating a new Employee with information: {}", employee);

        List<EmployeeDepartmentHistory> employeeDepartmentHistories = employee.getEmployeeDepartmentHistories();
        List<EmployeePayHistory> employeePayHistories = employee.getEmployeePayHistories();
        List<JobCandidate> jobCandidates = employee.getJobCandidates();
        if(employeeDepartmentHistories != null && Hibernate.isInitialized(employeeDepartmentHistories)) {
            employeeDepartmentHistories.forEach(_employeeDepartmentHistory -> _employeeDepartmentHistory.setEmployee(employee));
        }
        if(employeePayHistories != null && Hibernate.isInitialized(employeePayHistories)) {
            employeePayHistories.forEach(_employeePayHistory -> _employeePayHistory.setEmployee(employee));
        }
        if(jobCandidates != null && Hibernate.isInitialized(jobCandidates)) {
            jobCandidates.forEach(_jobCandidate -> _jobCandidate.setEmployee(employee));
        }

        Employee employeeCreated = this.wmGenericDao.create(employee);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(employeeCreated);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee getById(Integer employeeId) {
        LOGGER.debug("Finding Employee by id: {}", employeeId);
        return this.wmGenericDao.findById(employeeId);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee findById(Integer employeeId) {
        LOGGER.debug("Finding Employee by id: {}", employeeId);
        try {
            return this.wmGenericDao.findById(employeeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No Employee found with id: {}", employeeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public List<Employee> findByMultipleIds(List<Integer> employeeIds, boolean orderedReturn) {
        LOGGER.debug("Finding Employees by ids: {}", employeeIds);

        return this.wmGenericDao.findByMultipleIds(employeeIds, orderedReturn);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee getByLoginId(String loginId) {
        Map<String, Object> loginIdMap = new HashMap<>();
        loginIdMap.put("loginId", loginId);

        LOGGER.debug("Finding Employee by unique keys: {}", loginIdMap);
        return this.wmGenericDao.findByUniqueKey(loginIdMap);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee getByRowguid(String rowguid) {
        Map<String, Object> rowguidMap = new HashMap<>();
        rowguidMap.put("rowguid", rowguid);

        LOGGER.debug("Finding Employee by unique keys: {}", rowguidMap);
        return this.wmGenericDao.findByUniqueKey(rowguidMap);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee getByNationalIdnumber(String nationalIdnumber) {
        Map<String, Object> nationalIdnumberMap = new HashMap<>();
        nationalIdnumberMap.put("nationalIdnumber", nationalIdnumber);

        LOGGER.debug("Finding Employee by unique keys: {}", nationalIdnumberMap);
        return this.wmGenericDao.findByUniqueKey(nationalIdnumberMap);
    }

    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee update(Employee employee) {
        LOGGER.debug("Updating Employee with information: {}", employee);

        List<EmployeeDepartmentHistory> employeeDepartmentHistories = employee.getEmployeeDepartmentHistories();
        List<EmployeePayHistory> employeePayHistories = employee.getEmployeePayHistories();
        List<JobCandidate> jobCandidates = employee.getJobCandidates();
        if(employeeDepartmentHistories != null && Hibernate.isInitialized(employeeDepartmentHistories)) {
            employeeDepartmentHistories.forEach(_employeeDepartmentHistory -> _employeeDepartmentHistory.setEmployee(employee));
        }
        if(employeePayHistories != null && Hibernate.isInitialized(employeePayHistories)) {
            employeePayHistories.forEach(_employeePayHistory -> _employeePayHistory.setEmployee(employee));
        }
        if(jobCandidates != null && Hibernate.isInitialized(jobCandidates)) {
            jobCandidates.forEach(_jobCandidate -> _jobCandidate.setEmployee(employee));
        }

        this.wmGenericDao.update(employee);
        this.wmGenericDao.refresh(employee);

        return employee;
    }
    
    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee partialUpdate(Integer employeeId, Map<String, Object>employeePatch) {
        LOGGER.debug("Partially Updating the Employee with id: {}", employeeId);
        
        Employee employee = getById(employeeId);

        try {
            ObjectReader employeeReader = this.objectMapper.readerForUpdating(employee);
            employee = employeeReader.readValue(this.objectMapper.writeValueAsString(employeePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", employeePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        employee = update(employee);

        return employee;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public Employee delete(Integer employeeId) {
        LOGGER.debug("Deleting Employee with id: {}", employeeId);
        Employee deleted = this.wmGenericDao.findById(employeeId);
        if (deleted == null) {
            LOGGER.debug("No Employee found with id: {}", employeeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), Employee.class.getSimpleName(), employeeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AdventureWorks2014TransactionManager")
    @Override
    public void delete(Employee employee) {
        LOGGER.debug("Deleting Employee with {}", employee);
        this.wmGenericDao.delete(employee);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<Employee> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<Employee> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all Employees");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AdventureWorks2014 for table Employee to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AdventureWorks2014 for table Employee to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<EmployeeDepartmentHistory> findAssociatedEmployeeDepartmentHistories(Integer businessEntityId, Pageable pageable) {
        LOGGER.debug("Fetching all associated employeeDepartmentHistories");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employee.businessEntityId = '" + businessEntityId + "'");

        return employeeDepartmentHistoryService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<EmployeePayHistory> findAssociatedEmployeePayHistories(Integer businessEntityId, Pageable pageable) {
        LOGGER.debug("Fetching all associated employeePayHistories");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employee.businessEntityId = '" + businessEntityId + "'");

        return employeePayHistoryService.findAll(queryBuilder.toString(), pageable);
    }

    @Transactional(readOnly = true, value = "AdventureWorks2014TransactionManager")
    @Override
    public Page<JobCandidate> findAssociatedJobCandidates(Integer businessEntityId, Pageable pageable) {
        LOGGER.debug("Fetching all associated jobCandidates");

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("employee.businessEntityId = '" + businessEntityId + "'");

        return jobCandidateService.findAll(queryBuilder.toString(), pageable);
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service EmployeePayHistoryService instance
     */
    protected void setEmployeePayHistoryService(EmployeePayHistoryService service) {
        this.employeePayHistoryService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service JobCandidateService instance
     */
    protected void setJobCandidateService(JobCandidateService service) {
        this.jobCandidateService = service;
    }

    /**
     * This setter method should only be used by unit tests
     *
     * @param service EmployeeDepartmentHistoryService instance
     */
    protected void setEmployeeDepartmentHistoryService(EmployeeDepartmentHistoryService service) {
        this.employeeDepartmentHistoryService = service;
    }

}