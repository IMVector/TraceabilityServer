package com.ecnu.traceability.service;

import com.ecnu.traceability.entity.IsolateSatistic;
import com.ecnu.traceability.mapper.IsolateSatisticMapper;
import com.ecnu.traceability.mapper.IsolateStatesMapper;
import com.ecnu.traceability.one_net.OneNETDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service
public class IsolateStatisticService {

    @Autowired
    private IsolateSatisticMapper IsolateSatisticDao;

    public IsolateSatistic getlastItemData() {

        return IsolateSatisticDao.getLastItemData();

    }

    public void pushIsolateMonthDataToOneNET() {

        IsolateSatistic isolateSatistic = IsolateSatisticDao.getLastItemData();

        if (null != isolateSatistic) {
            System.out.println("======================");
            System.out.println("======================");
            System.out.println("======================");
            System.out.println("======================");
            System.out.println("======================");
            System.out.println("======================");
            System.out.println("======================");
            System.out.println(isolateSatistic.toString());
            OneNETDevice.pushIsolateMonthData(isolateSatistic);
        }

    }

    public boolean addIsolateStatistic(int month, String flag) {

        IsolateSatistic isoSatistic = IsolateSatisticDao.getLastItemData();

        if (null == isoSatistic) {
            isoSatistic = new IsolateSatistic(
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0, new Date());

            try {
                IsolateSatisticDao.addIsolateSatistic(isoSatistic);
                addIsolateStatistic(month, flag);
                pushIsolateMonthDataToOneNET();
                return true;

            } catch (Exception e) {
                System.out.println(e);
                return false;
            }

        } else {

            try {
                isoSatistic.updateData(month, flag);
                System.out.println("++++++++++++++++++++");
                System.out.println("++++++++++++++++++++");
                System.out.println("++++++++++++++++++++");
                System.out.println(isoSatistic.toString());
                System.out.println("++++++++++++++++++++");
                System.out.println("++++++++++++++++++++");
                IsolateSatisticDao.updateIsolateSatistic(isoSatistic);
                pushIsolateMonthDataToOneNET();

                return true;
            } catch (Exception e) {
                System.out.println(e);
                return false;
            }

        }


    }


}