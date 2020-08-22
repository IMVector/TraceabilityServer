package com.ecnu.traceability.mapper;

import com.ecnu.traceability.entity.IsolateSatistic;
import org.springframework.stereotype.Repository;

@Repository
public interface IsolateSatisticMapper {

    public void addIsolateSatistic(IsolateSatistic isolateSatistic);

    public IsolateSatistic getLastItemData();

    public void updateIsolateSatistic(IsolateSatistic isolateSatistic);

}
