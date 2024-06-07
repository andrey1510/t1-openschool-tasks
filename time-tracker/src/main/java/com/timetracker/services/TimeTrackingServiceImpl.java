package com.timetracker.services;

import com.timetracker.dto.MethodDurationStatistics;
import com.timetracker.models.MethodExecutionRecord;
import com.timetracker.repositories.TimeTrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeTrackingServiceImpl implements TimeTrackingService {

    private final TimeTrackingRepository timeTrackingRepository;

    @Override
    @Transactional
    public MethodExecutionRecord createOperationRecord(MethodExecutionRecord methodExecutionRecord) {
        return timeTrackingRepository.save(methodExecutionRecord);
    }

    @Override
    @Transactional
    public List<MethodExecutionRecord> getAllCalls() {
        return timeTrackingRepository.findAll();
    }

    @Override
    @Transactional
    public List<MethodDurationStatistics> getDurationStatistics(){
        return timeTrackingRepository.getDurationStatistics();
    }

    @Override
    @Transactional
    public List<MethodDurationStatistics> getDurationStatisticsFilterByClass(String className){
        return timeTrackingRepository.getDurationStatisticsFilterByClass(className);
    }

    @Override
    @Transactional
    public List<MethodDurationStatistics> getDurationStatisticsFilterByPackage(String packageName){
        return timeTrackingRepository.getDurationStatisticsFilterByPackage(packageName);
    }
}
