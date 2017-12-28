import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Timer;


public class GetNetInfo {
  private String Nasip="";
  private String LocalHostIP="";
  private String Mac;
  InetAddress IP;
  String timestamp ;
  String secret = "Eshore!@#";
  String iswifiMobile = "4060";
  String iswifiPC = "1050";
  String md5;
  String version="214";
  String NetStatus;
  String LocattionIP;
  public String getLocattionIP() {
	return LocattionIP;
}



public String getNetStatus() {
	return NetStatus;
}



	public GetNetInfo() throws IOException,MalformedURLException,UnknownHostException,SocketException {
		
		
		GetNasip();
		GetLocalHost();
		GetMac();
		GetTime();
	}
	
public String getNasip() {
		return Nasip;
	}

	public String getLocalHostIP() {
		return LocalHostIP;
	}

	public String getMac() {
		return Mac;
	}

	public InetAddress getIP() {
		return IP;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getSecret() {
		return secret;
	}

	public String getIswifiMobile() {
		return iswifiMobile;
	}

	public String getIswifiPC() {
		return iswifiPC;
	}

private  String GetNasip() throws IOException,MalformedURLException 
{  
	  URL url=new URL("http://www.baidu.com");
		HttpURLConnection con=(HttpURLConnection) url.openConnection();
	con.getResponseCode(); 
	String ResponseCode=con.getURL().toString();
	int c=0;
	if(!(ResponseCode.equals("http://www.baidu.com")))
	{
	String str[]=ResponseCode.split("=");
	String s[]=str[1].split("&");
	Nasip=s[0];
	LocattionIP=str[2];
	}
	if(ResponseCode.equals("http://www.baidu.com"))
	{
		NetStatus="ÒÑÁ¬½ÓInternet";
	}
	else
		NetStatus="";
	
		
return Nasip;
}

private String GetLocalHost() throws UnknownHostException {

IP=InetAddress.getLocalHost();
LocalHostIP=IP.getHostAddress();
	return LocalHostIP;
}
private String GetMac() throws SocketException {
		Mac=AddressUtil.getLocalMac(IP);
	return Mac;
}
public void GetTime(){
	timestamp = System.currentTimeMillis() + "";
	
}
public String MD5(String str)
{
	md5=MD5Util.MD5(str);
	return md5;
}
}
