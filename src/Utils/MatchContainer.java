package Utils;

import Entities.MatchEntity;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;

import java.util.Collections;
import java.util.Iterator;

public class MatchContainer {
    private final CosmosContainer container;
    public MatchContainer() {
        this.container = AzureCosmosClient.getContainer();
    }

    public void save(MatchEntity match) {
        container.createItem(match, new PartitionKey(match.matchId), new CosmosItemRequestOptions());
    }

    public MatchEntity getByMatchId(String matchId) {
        String query = "SELECT * FROM c WHERE c.matchId = @matchId";
        SqlQuerySpec querySpec = new SqlQuerySpec(query,
                Collections.singletonList(new SqlParameter("@matchId", matchId)));

        CosmosPagedIterable<MatchEntity> results = container.queryItems(querySpec, new CosmosQueryRequestOptions(), MatchEntity.class);

        Iterator<MatchEntity> iterator = results.iterator();
        return iterator.hasNext() ? iterator.next() : null;
    }
}
