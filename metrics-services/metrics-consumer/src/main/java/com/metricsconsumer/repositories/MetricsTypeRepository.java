package com.metricsconsumer.repositories;

import com.metricsconsumer.dto.MetricsTypeDTO;
import com.metricsconsumer.models.MetricsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MetricsTypeRepository extends JpaRepository<MetricsType, String> {

    @Query("SELECT new com.metricsconsumer.dto.MetricsTypeDTO(" +
        "mt.name, " +
        "mt.description) " +
        "FROM MetricsType mt")
    List<MetricsTypeDTO> findMetricsTypes();

}
