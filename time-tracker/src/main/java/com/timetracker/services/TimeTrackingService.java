package com.timetracker.services;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.models.MethodExecutionRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TimeTrackingService {


    @Transactional
    MethodExecutionRecord createOperationRecord(MethodExecutionRecord methodExecutionRecord);

    @Transactional
    List<MethodExecutionRecord> getAllCalls();

    @Transactional
    List<MethodDurationStatistics> getDurationStatistics();

    @Transactional
    List<MethodDurationStatistics> getDurationStatisticsFilterByClass(String className);

    @Transactional
    List<MethodDurationStatistics> getDurationStatisticsFilterByPackage(String packageName);
}
