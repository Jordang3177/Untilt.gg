package Utils.Containers;

import Entities.PlayerDataEntity;
import Entities.PlayerEntity;
import Utils.AzureCosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;

import java.util.Collections;
import java.util.Iterator;

public class PlayerDataContainer {
    private final CosmosContainer playerDataContainer;

    public PlayerDataContainer() {
        this.playerDataContainer = AzureCosmosClient.getPlayerDataContainer();
    }

    public void save(PlayerDataEntity playerDataEntity) {
        playerDataContainer.createItem(playerDataEntity, new PartitionKey(playerDataEntity.id), new CosmosItemRequestOptions());
    }

    public PlayerDataEntity getByPlayerId(String playerId) {
        String query = "SELECT * FROM c WHERE c.id = @id";
        SqlQuerySpec querySpec = new SqlQuerySpec(query,
                Collections.singletonList(new SqlParameter("@id", playerId)));

        CosmosPagedIterable<PlayerDataEntity> results = playerDataContainer.queryItems(querySpec, new CosmosQueryRequestOptions(), PlayerDataEntity.class);

        Iterator<PlayerDataEntity> iterator = results.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }

    public void replace(PlayerDataEntity playerDataEntity) {
        playerDataContainer.replaceItem(playerDataEntity, playerDataEntity.id, new PartitionKey(playerDataEntity.id), new CosmosItemRequestOptions());
    }
}
