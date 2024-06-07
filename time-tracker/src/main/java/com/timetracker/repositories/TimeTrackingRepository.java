package com.timetracker.repositories;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.models.MethodExecutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TimeTrackingRepository extends JpaRepository<MethodExecutionRecord, UUID> {

    @Query("SELECT new com.timetracker.dto.MethodDurationStatistics(" +
        "m.methodSignature, " +
        "m.className, " +
        "m.packageName, " +
        "SUM(m.duration), " +
        "ROUND(AVG(m.duration)), " +
        "MIN(m.duration), " +
        "MAX(m.duration), " +
        "ROUND(STDDEV(m.duration)), " +
        "COUNT(m)) " +
        "FROM MethodExecutionRecord m " +
        "GROUP BY m.methodSignature, m.className, m.packageName")
    List<MethodDurationStatistics> getDurationStatistics();

    @Query("SELECT new com.timetracker.dto.MethodDurationStatistics(" +
        "m.methodSignature, " +
        "m.className, " +
        "m.packageName, " +
        "SUM(m.duration), " +
        "ROUND(AVG(m.duration)), " +
        "MIN(m.duration), " +
        "MAX(m.duration), " +
        "ROUND(STDDEV(m.duration)), " +
        "COUNT(m)) " +
        "FROM MethodExecutionRecord m " +
        "WHERE m.className = :methodClass " +
        "GROUP BY m.methodSignature, m.className, m.packageName")
    List<MethodDurationStatistics> getDurationStatisticsFilterByClass(@Param("methodClass") String methodClass);

    @Query("SELECT new com.timetracker.dto.MethodDurationStatistics(" +
        "m.methodSignature, " +
        "m.className, " +
        "m.packageName, " +
        "SUM(m.duration), " +
        "ROUND(AVG(m.duration)), " +
        "MIN(m.duration), " +
        "MAX(m.duration), " +
        "ROUND(STDDEV(m.duration)), " +
        "COUNT(m)) " +
        "FROM MethodExecutionRecord m " +
        "WHERE m.className = :methodPackage " +
        "GROUP BY m.methodSignature, m.className, m.packageName")
    List<MethodDurationStatistics> getDurationStatisticsFilterByPackage(@Param("methodPackage") String methodPackage);
}
