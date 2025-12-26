package Entities;

import Entities.Info.SummonerInfoEntity;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class MatchEntity {
    public String id;
    public String matchId;
    public long gameCreationTimeStamp;
    public TimeZoneEntity timeZoneEntity;
    public List<SummonerInfoEntity> summonersInfo;
    public List<PlayerEntity> winners;

    public MatchEntity() {}
}
