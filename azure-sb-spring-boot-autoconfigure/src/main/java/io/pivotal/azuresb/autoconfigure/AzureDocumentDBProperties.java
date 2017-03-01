package io.pivotal.azuresb.autoconfigure;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author pbopardikar This class will be instantiated by Spring Boot. The
 *         attributes defined in this class will get their values from the Cloud
 *         Foundry DocumentDB service instance. The name of the CF Service
 *         Instance and the DocumentDB Resource ID is defined in the
 *         azure-sb-documentdb-client project's application.properties
 */
@ConfigurationProperties("azure.documentdb")
public class AzureDocumentDBProperties extends AzureProperties {

	/**
	 * Adding default values for the below attributes just so the Tests pass
	 * when running Maven builds
	 */
	@Value("${azure.documentdb.resource.id:myresource}")
	private String resourceId;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_host_endpoint:myhost}")
	private String hostEndpoint;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_master_key:mymasterkey}")
	private String masterKey;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_database_id:mydatabaseid}")
	private String databaseId;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_database_link:mydatabaselink}")
	private String link;

	@Override
	protected void populateCallback(JSONObject credentials) {
		// revisit: need to implement this method?
		System.out.println("INSIDE AzureDocumentDBProperties.populateCallback");
	}

	@PostConstruct
	private void populateProperties() {
		super.populate("");
		System.out
				.println("INSIDE AzureDocumentDBProperties.populateProperties");
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getHostEndpoint() {
		return hostEndpoint;
	}

	public void setHostEndpoint(String hostEndpoint) {
		this.hostEndpoint = hostEndpoint;
	}

	public String getMasterKey() {
		return masterKey;
	}

	public void setMasterKey(String masterKey) {
		this.masterKey = masterKey;
	}

	public String getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(String databaseId) {
		this.databaseId = databaseId;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
