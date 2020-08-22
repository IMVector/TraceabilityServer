package com.ecnu.traceability.mapper;

import com.ecnu.traceability.entity.LocationDataMiningResult;

import java.util.List;

public interface LocationDMResultMapper {

    public void addData(LocationDataMiningResult result);

    public List<LocationDMResultMapper> getAllCenterResultList();
    public List<LocationDMResultMapper> getAllResultList();

    public List<LocationDMResultMapper> getCenterResultByBatch(Integer batch);
    public List<LocationDMResultMapper> getResultByBatch(Integer batch);

    public void deleteAllData();

    public Integer getMaxBatch();
}
