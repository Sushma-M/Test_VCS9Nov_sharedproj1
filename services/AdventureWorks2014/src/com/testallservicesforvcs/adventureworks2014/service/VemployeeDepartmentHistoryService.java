/*Copyright (c) 2016-2017 vcstest4.com All Rights Reserved.
 This software is the confidential and proprietary information of vcstest4.com You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with vcstest4.com*/
package com.testallservicesforvcs.adventureworks2014.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import com.testallservicesforvcs.adventureworks2014.VemployeeDepartmentHistory;
import com.testallservicesforvcs.adventureworks2014.VemployeeDepartmentHistoryId;

/**
 * Service object for domain model class {@link VemployeeDepartmentHistory}.
 */
public interface VemployeeDepartmentHistoryService {

    /**
     * Creates a new VemployeeDepartmentHistory. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VemployeeDepartmentHistory if any.
     *
     * @param vemployeeDepartmentHistory Details of the VemployeeDepartmentHistory to be created; value cannot be null.
     * @return The newly created VemployeeDepartmentHistory.
     */
    VemployeeDepartmentHistory create(@Valid VemployeeDepartmentHistory vemployeeDepartmentHistory);


	/**
     * Returns VemployeeDepartmentHistory by given id if exists.
     *
     * @param vemployeedepartmenthistoryId The id of the VemployeeDepartmentHistory to get; value cannot be null.
     * @return VemployeeDepartmentHistory associated with the given vemployeedepartmenthistoryId.
	 * @throws EntityNotFoundException If no VemployeeDepartmentHistory is found.
     */
    VemployeeDepartmentHistory getById(VemployeeDepartmentHistoryId vemployeedepartmenthistoryId);

    /**
     * Find and return the VemployeeDepartmentHistory by given id if exists, returns null otherwise.
     *
     * @param vemployeedepartmenthistoryId The id of the VemployeeDepartmentHistory to get; value cannot be null.
     * @return VemployeeDepartmentHistory associated with the given vemployeedepartmenthistoryId.
     */
    VemployeeDepartmentHistory findById(VemployeeDepartmentHistoryId vemployeedepartmenthistoryId);

	/**
     * Find and return the list of VemployeeDepartmentHistories by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param vemployeedepartmenthistoryIds The id's of the VemployeeDepartmentHistory to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return VemployeeDepartmentHistories associated with the given vemployeedepartmenthistoryIds.
     */
    List<VemployeeDepartmentHistory> findByMultipleIds(List<VemployeeDepartmentHistoryId> vemployeedepartmenthistoryIds, boolean orderedReturn);


    /**
     * Updates the details of an existing VemployeeDepartmentHistory. It replaces all fields of the existing VemployeeDepartmentHistory with the given vemployeeDepartmentHistory.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VemployeeDepartmentHistory if any.
     *
     * @param vemployeeDepartmentHistory The details of the VemployeeDepartmentHistory to be updated; value cannot be null.
     * @return The updated VemployeeDepartmentHistory.
     * @throws EntityNotFoundException if no VemployeeDepartmentHistory is found with given input.
     */
    VemployeeDepartmentHistory update(@Valid VemployeeDepartmentHistory vemployeeDepartmentHistory);
    
    
    /**
     * Partially updates the details of an existing VemployeeDepartmentHistory. It updates only the
     * fields of the existing VemployeeDepartmentHistory which are passed in the vemployeeDepartmentHistoryPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on VemployeeDepartmentHistory if any.
     *
     * @param vemployeedepartmenthistoryId The id of the VemployeeDepartmentHistory to be deleted; value cannot be null.
     * @param vemployeeDepartmentHistoryPatch The partial data of VemployeeDepartmentHistory which is supposed to be updated; value cannot be null.
     * @return The updated VemployeeDepartmentHistory.
     * @throws EntityNotFoundException if no VemployeeDepartmentHistory is found with given input.
     */
    VemployeeDepartmentHistory partialUpdate(VemployeeDepartmentHistoryId vemployeedepartmenthistoryId, Map<String, Object> vemployeeDepartmentHistoryPatch);

    /**
     * Deletes an existing VemployeeDepartmentHistory with the given id.
     *
     * @param vemployeedepartmenthistoryId The id of the VemployeeDepartmentHistory to be deleted; value cannot be null.
     * @return The deleted VemployeeDepartmentHistory.
     * @throws EntityNotFoundException if no VemployeeDepartmentHistory found with the given id.
     */
    VemployeeDepartmentHistory delete(VemployeeDepartmentHistoryId vemployeedepartmenthistoryId);

    /**
     * Deletes an existing VemployeeDepartmentHistory with the given object.
     *
     * @param vemployeeDepartmentHistory The instance of the VemployeeDepartmentHistory to be deleted; value cannot be null.
     */
    void delete(VemployeeDepartmentHistory vemployeeDepartmentHistory);

    /**
     * Find all VemployeeDepartmentHistories matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VemployeeDepartmentHistories.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<VemployeeDepartmentHistory> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all VemployeeDepartmentHistories matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching VemployeeDepartmentHistories.
     *
     * @see Pageable
     * @see Page
     */
    Page<VemployeeDepartmentHistory> findAll(String query, Pageable pageable);

    /**
     * Exports all VemployeeDepartmentHistories matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all VemployeeDepartmentHistories matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the VemployeeDepartmentHistories in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the VemployeeDepartmentHistory.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}