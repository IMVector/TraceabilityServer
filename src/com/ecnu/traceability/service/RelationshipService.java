package com.ecnu.traceability.service;

import com.ecnu.traceability.entity.Relationship;
import com.ecnu.traceability.mapper.RelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RelationshipService {

    @Autowired
    private RelationshipMapper relationshipDao;

    public List<Relationship> getRelationship() {
        List<Relationship> relationshipList = relationshipDao.getRelationshipList();
        return relationshipList;
    }

//    public List<Relationship> getRelationshipByMac(String mac) {
//        List<Relationship> relationshipList = relationshipDao.getRelationshipByMac(mac);
//        return relationshipList;
//    }

    public boolean addRelationshipList(List<Relationship> relationshipList) {
        boolean flag = true;
        for (Relationship relationship : relationshipList) {
            List<Relationship> patientList = relationshipDao.getRelationshipByMac(relationship.getTargetMac(), relationship.getOriginMac());
            if (patientList.size() > 0) {//数据库已经存在
                System.out.println("=========================here1==========================");

                for (Relationship rsp : patientList) {
                    rsp.setFlag(0);
                    try {
                        relationshipDao.updateRelationship(rsp);

                    } catch (Exception e) {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                }
            } else {//数据库不存在
                System.out.println("=========================here2==========================");

                List<Relationship> tempList = relationshipDao.getRelationshipByMac(relationship.getOriginMac(), relationship.getTargetMac());
                if(tempList.size()>0){//已经存在该数据跳出
                    System.out.println("=========================repeat==========================");
                    continue;
                }
                try {
                    relationshipDao.addRelationship(relationship);
                } catch (Exception e) {
                    flag = false;
                    System.out.println(e);
                    e.printStackTrace();
                }
            }

        }
        return flag;
    }
}
