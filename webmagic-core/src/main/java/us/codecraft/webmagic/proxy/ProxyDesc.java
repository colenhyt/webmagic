package us.codecraft.webmagic.proxy;

public class ProxyDesc {
	String user = null;
	String pwd = null;
	String ip = null;
	String port = null;
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	String protocol = null;
	int type;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	public String[] toArray(){
		String[] array = new String[4];
		array[0] = user;
		array[1] = pwd;
		array[2] = ip;
		array[3] = port;
		return array;
	}
	
	public String toString(){
		return ip+","+port;
	}
}
