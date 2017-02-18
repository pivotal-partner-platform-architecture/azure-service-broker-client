package io.pivotal.azuresb.autoconfigure;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

/**
 * 
 * @author pbopardikar This class will be instantiated by Spring Boot. The
 *         attributes defined in this class will get their values from the Cloud
 *         Foundry DocumentDB service instance. The name of the CF Service
 *         Instance and the DocumentDB Resource ID is defined in the
 *         azure-sb-documentdb-client project's application.properties
 */
@ConfigurationProperties()
public class AzureDocumentDBProperties extends AzureProperties {

	@Autowired
	private Environment environment;

	/**
	 * Adding default values for the below attributes just so the Tests pass
	 * when running Maven builds
	 */

	@Value("${azure.documentdb.resource.id:myresource}")
	private String documentdbResourceId;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_host_endpoint:myhost}")
	private String documentdbHostEndpoint;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_master_key:mymasterkey}")
	private String documentdbMasterKey;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_database_id:mydatabaseid}")
	private String documentdbDatabaseId;

	@Value("${vcap.services.${azure.documentdb.service.instance:myservice}.credentials.documentdb_database_link:mydatabaselink}")
	private String documentdbLink;

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

	public String getDocumentdbResourceId() {
		return documentdbResourceId;
	}

	public void setDocumentdbResourceId(String documentdbResourceId) {
		this.documentdbResourceId = documentdbResourceId;
	}

	public String getDocumentdbHostEndpoint() {
		return documentdbHostEndpoint;
	}

	public void setDocumentdbHostEndpoint(String documentdbHostEndpoint) {
		this.documentdbHostEndpoint = documentdbHostEndpoint;
	}

	public String getDocumentdbMasterKey() {
		return documentdbMasterKey;
	}

	public void setDocumentdbMasterKey(String documentdbMasterKey) {
		this.documentdbMasterKey = documentdbMasterKey;
	}

	public String getDocumentdbDatabaseId() {
		return documentdbDatabaseId;
	}

	public void setDocumentdbDatabaseId(String documentdbDatabaseId) {
		this.documentdbDatabaseId = documentdbDatabaseId;
	}

	public String getDocumentdbLink() {
		return documentdbLink;
	}

	public void setDocumentdbLink(String documentdbLink) {
		this.documentdbLink = documentdbLink;
	}

}
