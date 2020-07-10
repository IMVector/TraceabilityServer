package com.ecnu.traceability.mapper;

import com.ecnu.traceability.entity.Relationship;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RelationshipMapper {

    public void addRelationship(Relationship relationship);
    public List<Relationship> getRelationshipList();
    public List<Relationship> getRelationshipByMac(String originMac,String targetMac);
    public List<Relationship> getRelationshipByOriginMac(String mac);
    public List<Relationship> getRelationshipByTargetMac(String mac);
    public void updateRelationship(Relationship relationship);

}
