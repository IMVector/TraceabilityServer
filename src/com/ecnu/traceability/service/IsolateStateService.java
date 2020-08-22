package com.ecnu.traceability.service;

import com.ecnu.traceability.entity.IsolateState;
import com.ecnu.traceability.mapper.IsolateStatesMapper;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class IsolateStateService {

    @Autowired
    private IsolateStatesMapper IsolateStateDao;

    @Autowired
    private IsolateStatisticService isolateStatisticService;

    public void pushIsolateInfoToOneNET() {
        int isoNum = getIsolateNum();
        OneNETDevice.pushIsolateNum(isoNum);

        int nonIsoNum = getNoIsolateNum();
        OneNETDevice.pushNoIsolateNum(nonIsoNum);
    }

    public int getNoIsolateNum() {
        List<IsolateState> list = IsolateStateDao.getNoIsolateList();
        if (null != list) {
            return list.size();
        } else {
            return 0;
        }
    }

    public int getIsolateNum() {

        List<IsolateState> list = IsolateStateDao.getIsolateList();
        if (null != list) {
            return list.size();
        } else {
            return 0;
        }
    }

    /**
     * 先查看有没有，没有的话向里面添加
     *
     * @param state
     * @return
     */
    public boolean addIsolateState(IsolateState state) {

        // 修改
        Date date = new Date();
        int month = date.getMonth()+1;

        String flag = "";
        if (state.isState()) {
            flag = "in";
        } else {
            flag = "out";
        }

        isolateStatisticService.addIsolateStatistic(month, flag);


        IsolateState stateExist = IsolateStateDao.getIsolateStateByMac(state.getMac());
        if (null != stateExist) {
            try {
                stateExist.setState(state.isState());
                IsolateStateDao.updateIsolateState(stateExist);

                pushIsolateInfoToOneNET();//更新云平台数据
                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }

        try {
            IsolateStateDao.addIsolateState(state);
            pushIsolateInfoToOneNET();//更新云平台数据

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }


}
