package io.pivotal.azuresb.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(AzureDocumentDBProperties.class)
public class AzureDocumentDBAutoConfiguration {

	private final AzureDocumentDBProperties properties;

	@Autowired
	public AzureDocumentDBAutoConfiguration(AzureDocumentDBProperties properties) {
		this.properties = properties;
	}

	@Bean
	public DocumentClient documentClient() {
		String hostname = properties.getHostEndpoint();
		String masterkey = properties.getMasterKey();
		return new DocumentClient(hostname, masterkey,
				ConnectionPolicy.GetDefault(), ConsistencyLevel.Session);
	}

}
