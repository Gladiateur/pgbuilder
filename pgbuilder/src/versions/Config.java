/*
 * Config.java 18/10/5
 */

package versions;

public class Config {
	private String suffix;
	private String package0;
	private String impl;
	private String implSuffix ;
	private String implPath ;
	private String servicePath;
	
	private String interfaceName;
	private String filePath;
	
	private String filePerfix = "I";
	private String fileSuffix;
	
	public String getFilePerfix() {
		return filePerfix;
	}
	public void setFilePerfix(String filePerfix) {
		this.filePerfix = filePerfix;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getPackage0() {
		return package0;
	}
	public void setPackage0(String package0) {
		this.package0 = package0;
	}
	public String getImpl() {
		return impl;
	}
	public void setImpl(String impl) {
		this.impl = impl;
	}
	public String getImplSuffix() {
		return implSuffix;
	}
	public void setImplSuffix(String implSuffix) {
		this.implSuffix = implSuffix;
	}
	public String getImplPath() {
		return implPath;
	}
	public void setImplPath(String implPath) {
		this.implPath = implPath;
	}
	public String getServicePath() {
		return servicePath;
	}
	public void setServicePath(String servicePath) {
		this.servicePath = servicePath;
	}
}
