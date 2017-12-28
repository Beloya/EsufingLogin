import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
public class PropertiesUtils {
	String UserName;
	String PassWord;
	String Remember;
	String lasttimes;
	String lastNasip;
	 String IsWifiStatus="";
	  String IPGetStatus="";
	  String lastclientip;
	  String Nasip="";
	  public String getNasip() {
		return Nasip;
	}
	public String getGetNasipType() {
		return GetNasipType;
	}
	String GetNasipType="";
	  
public void Read() throws IOException{
	InputStream is=new BufferedInputStream(new FileInputStream("config.properties"));
	 Properties p = new Properties();
	
	     p.load(is);
	     Iterator<String> it=p.stringPropertyNames().iterator();
	  
	    is.close();
	    
	 UserName=p.getProperty("UserName"); 
	 PassWord = p.getProperty("Password");
	 Remember=p.getProperty("Remember");
	 lasttimes=p.getProperty("lasttimes"); 
	 lastNasip = p.getProperty("lastNasip");
	 IsWifiStatus=p.getProperty("IsWifiStatus");
	 IPGetStatus=p.getProperty("IPGetStatus");
	 
	 lastclientip=p.getProperty("lastclientip");
	 Nasip=p.getProperty("Nasip");
	 GetNasipType=p.getProperty("GetNasipType");
}
public String getUserName() {
	return UserName;
}
public String getPassWord() {
	return PassWord;
}
public String getRemember() {
	return Remember;
}
public void Write(String UserName,String Password,String Remember) throws FileNotFoundException{
	FileOutputStream oFile = new FileOutputStream("config.properties", true);
	Properties p = new Properties();
	p.setProperty("UserName",UserName);
	p.setProperty("Password",Password);
	p.setProperty("Remember",Remember);
	try {
		
	    p.store(oFile, "info");
	    } 
	catch (IOException ex) {} 
}
public void WriteCach(String clientip,String lastNasip,String lasttimes,String loginstatus) throws FileNotFoundException{
	FileOutputStream oFile = new FileOutputStream("config.properties", true);
	Properties p = new Properties();
	p.setProperty("lastclientip",clientip);
	p.setProperty("lastNasip",lastNasip);
	p.setProperty("lasttimes",lasttimes);
	p.setProperty("loginstatus",loginstatus);
	try {
		
	    p.store(oFile, "cach");
	    } 
	catch (IOException ex) {} 
}
public String getLastclientip() {
	return lastclientip;
}
public String getLasttimes() {
	return lasttimes;
}
public String getLastNasip() {
	return lastNasip;
}
public String getIsWifiStatus() {
	return IsWifiStatus;
}
public String getIPGetStatus() {
	return IPGetStatus;
}
public void WriteOption(String IsWifiStatus,String IPGetStatus,String Nasip,String GetNasipType) throws FileNotFoundException{
	FileOutputStream oFile = new FileOutputStream("config.properties", true);
	Properties p = new Properties();
	p.setProperty("IsWifiStatus",IsWifiStatus);
	p.setProperty("IPGetStatus",IPGetStatus);
	if(GetNasipType.equals(1))
	p.setProperty("Nasip",Nasip);
	p.setProperty("GetNasipType",GetNasipType);
	try {
		
	    p.store(oFile, "cach");
	    } 
	catch (IOException ex) {} 
}
}
