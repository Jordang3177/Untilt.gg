package Utils;

import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.CosmosContainer;

public class AzureCosmosClient {
    public static CosmosContainer getContainer() {
        CosmosClient client = new CosmosClientBuilder().endpoint(Config.AZURE_COSMOS_DB_URI).key(Config.AZURE_COSMOS_DB_PRIMARY_KEY_READ_WRITE).consistencyLevel(ConsistencyLevel.EVENTUAL).buildClient();

        return client.getDatabase("untilt.gg").getContainer("matches");
    }
}
