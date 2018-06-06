/**
 * 
 */
package com.xianjinxia.trade.shared.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



/**
 * @author liujun
 * ext配置文件读取
 */
@Configuration
@ConfigurationProperties(prefix = "ext",ignoreInvalidFields = false)
public class ExtProperties {
	
	private final Swagger swagger = new Swagger();


	private final ActiveMqConfiguration activeMqConfiguration = new ActiveMqConfiguration();
	
	private final OldCashmanServerAddressConfig oldCashmanServerAddressConfig = new OldCashmanServerAddressConfig();

	private final SmsConfig smsConfig = new SmsConfig();
    

    public Swagger getSwagger() {
    	return swagger;
    }
    
    public ActiveMqConfiguration getActiveMqConfiguration() {
    	return activeMqConfiguration;
    }
    
    

    public OldCashmanServerAddressConfig getOldCashmanServerAddressConfig() {
		return oldCashmanServerAddressConfig;
	}

	public SmsConfig getSmsConfig() {
		return smsConfig;
	}

	public static class Swagger {

        private String title = "uninoty API";
        
		private String description = "uninoty API documentation";

        private String version = "0.0.1";

        private String termsOfServiceUrl;

        private String contactName;

        private String contactUrl;

        private String contactEmail;

        private String license;

        private String licenseUrl;

        private Boolean enabled;

        public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getTermsOfServiceUrl() {
			return termsOfServiceUrl;
		}

		public void setTermsOfServiceUrl(String termsOfServiceUrl) {
			this.termsOfServiceUrl = termsOfServiceUrl;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public String getContactUrl() {
			return contactUrl;
		}

		public void setContactUrl(String contactUrl) {
			this.contactUrl = contactUrl;
		}

		public String getContactEmail() {
			return contactEmail;
		}

		public void setContactEmail(String contactEmail) {
			this.contactEmail = contactEmail;
		}

		public String getLicense() {
			return license;
		}

		public void setLicense(String license) {
			this.license = license;
		}

		public String getLicenseUrl() {
			return licenseUrl;
		}

		public void setLicenseUrl(String licenseUrl) {
			this.licenseUrl = licenseUrl;
		}

		public Boolean getEnabled() {
			return enabled;
		}

		public void setEnabled(Boolean enabled) {
			this.enabled = enabled;
		}


    }
    
    public static class ActiveMqConfiguration {

        private String brokerUrl;
        
        private String queueTableName;
        
        private String queueName;
        
        private Integer queueMaxCount;
        
        private Integer isCreateTable;
        
        public String getBrokerUrl() {
			return brokerUrl;
		}

		public void setBrokerUrl(String brokerUrl) {
			this.brokerUrl = brokerUrl;
		}

		public String getQueueTableName() {
			return queueTableName;
		}

		public void setQueueTableName(String queueTableName) {
			this.queueTableName = queueTableName;
		}

		public String getQueueName() {
			return queueName;
		}

		public void setQueueName(String queueName) {
			this.queueName = queueName;
		}

		public Integer getQueueMaxCount() {
			return queueMaxCount;
		}

		public void setQueueMaxCount(Integer queueMaxCount) {
			this.queueMaxCount = queueMaxCount;
		}

		public Integer getIsCreateTable() {
			return isCreateTable;
		}

		public void setIsCreateTable(Integer isCreateTable) {
			this.isCreateTable = isCreateTable;
		}

    }
    
     public static class OldCashmanServerAddressConfig{
		
		private String serverAddress;

		public String getServerAddress() {
			return serverAddress;
		}

		public void setServerAddress(String serverAddress) {
			this.serverAddress = serverAddress;
		}
	}

	public static class SmsConfig{
		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

    
}
