package com.metricsconsumer.repositories;

import com.metricsconsumer.dto.MetricsDataDTO;
import com.metricsconsumer.models.MetricsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MetricsDataRepository extends JpaRepository<MetricsData, UUID> {

    @Query("SELECT new com.metricsconsumer.dto.MetricsDataDTO(" +
        "m.timestamp, " +
        "m.value, " +
        "mt.baseUnit) " +
        "FROM MetricsData m " +
        "JOIN m.metricsType mt " +
        "WHERE mt.name = :name")
    List<MetricsDataDTO> findMetricsByName(@Param("name") String name);

}
