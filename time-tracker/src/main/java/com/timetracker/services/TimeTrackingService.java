package com.timetracker.services;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.models.MethodExecutionEntry;

import java.util.List;

public interface TimeTrackingService {

    MethodExecutionEntry createExecutionEntry(MethodExecutionEntry methodExecutionEntry);

    List<MethodExecutionEntry> getAllCalls();

    List<MethodDurationStatistics> getDurationStatistics();

    List<MethodDurationStatistics> getDurationStatisticsFilterByClass(String className);

    List<MethodDurationStatistics> getDurationStatisticsFilterByPackage(String packageName);

    void deleteAll();
}
