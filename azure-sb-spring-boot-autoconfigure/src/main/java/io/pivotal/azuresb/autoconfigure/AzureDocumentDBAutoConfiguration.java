package io.pivotal.azuresb.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microsoft.azure.documentdb.ConnectionPolicy;
import com.microsoft.azure.documentdb.ConsistencyLevel;
import com.microsoft.azure.documentdb.DocumentClient;

/**
 * 
 * @author pbopardikar This class instantiates the DocumentClient bean that will
 *         be injected in the azure-sb-documentdb-client project
 */
@Configuration
@ConditionalOnMissingBean(DocumentClient.class)
public class AzureDocumentDBAutoConfiguration {

	@Autowired
	private AzureDocumentDBProperties properties;

	public AzureDocumentDBProperties getProperties() {
		return properties;
	}

	public void setProperties(AzureDocumentDBProperties properties) {
		this.properties = properties;
	}

	@Bean
	public DocumentClient documentClient() {
		String hostname = properties.getDocumentdbHostEndpoint();
		String masterkey = properties.getDocumentdbMasterKey();
		DocumentClient documentClient = new DocumentClient(hostname, masterkey,
				ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
		return documentClient;
	}
}
