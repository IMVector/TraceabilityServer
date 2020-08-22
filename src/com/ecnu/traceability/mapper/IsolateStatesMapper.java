package com.ecnu.traceability.mapper;

import com.ecnu.traceability.entity.IsolateState;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IsolateStatesMapper {



    public void addIsolateState(IsolateState state);

    public void updateIsolateState(IsolateState state);

    public IsolateState getIsolateStateByMac(String mac);

    public List<IsolateState> getIsolateList();

    public List<IsolateState> getNoIsolateList();

}
