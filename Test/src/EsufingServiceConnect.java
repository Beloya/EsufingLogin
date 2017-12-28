import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.json.JSONObject;
public class EsufingServiceConnect implements Runnable{
	  static String Nasip="";
	 static String clientip="";
	  static String mac;
	  static String timestamp ;
	  static String secret = "Eshore!@#";
	  String iswifiMobile = "4060";
	  static String iswifiPC = "1050";
	  String md5;
	  static String version="214";
	  static String authenticator;
	  static String verifyCodeString;
	  static String UserName;
	  String Password;
	  String result;
	  String NetStatus;
	  JButton login;
	  String activeTime="15";
	  String RemeberBoolean="";
	  static   JTextArea LogArea;
	  static PropertiesUtils p=new PropertiesUtils();
	
	
public EsufingServiceConnect(String UserName,String Password,JButton login,String GetType,JTextArea LogArea,String RemeberBoolean) throws MalformedURLException, UnknownHostException, SocketException, IOException {
	GetNetInfo getinfo=new GetNetInfo();
   
	p.Read();
	//判断是否自定义获取nasip
	
	if(!(p.getGetNasipType().equals("1")))
	Nasip=getinfo.getNasip();
	else
		Nasip=p.getNasip();
	//判断获取ip方式
	
	if(p.getIPGetStatus().toString().equals("特殊"))
		clientip=getinfo.getLocattionIP();//用作路由器连接
	else
		clientip=getinfo.getLocalHostIP();
	mac=getinfo.getMac();
	timestamp=getinfo.getTimestamp();
	NetStatus=getinfo.getNetStatus();
	this.RemeberBoolean=RemeberBoolean;
	this.UserName=UserName;
	this.Password=Password;
	this.login=login;
	this.LogArea=LogArea;
	//判断登录模拟的终端
	if(p.getIsWifiStatus().equals("手机"))
			iswifiPC="4060";
	else
		iswifiPC="1060";
	
}
public static String getNasip() {
	return Nasip;
}
//验证信息
public static void verificate() throws MalformedURLException,IOException
{
	String str;
	GetNetInfo getinfo=new GetNetInfo();
	String strmd5="162"+timestamp;
	 authenticator=getinfo.MD5(strmd5);
	JSONObject json=new JSONObject();
	StringBuilder jsonstr = new StringBuilder();  
	URL url=new URL("http://enet.10000.gd.cn:10001/client/verificatecode");
	URLConnection uc=url.openConnection();
	json.put("schoolid", "162");
	json.put("username", UserName);
	json.put("timestamp", timestamp);
	uc.setDoOutput(true);
    uc.setDoInput(true);
    DataOutputStream out = new DataOutputStream(uc.getOutputStream());
out.writeBytes(json.toString());
    out.flush();
    uc.connect();
 InputStream is=uc.getInputStream();
    InputStreamReader ir=new InputStreamReader(is);
    BufferedReader br=new BufferedReader(ir);
   while((str=br.readLine())!=null)
    {
    	jsonstr.append(str);  	
    }
   str=jsonstr.toString();
   System.out.println("verificate:"+str);
}
//挑战信息获取
public static void vchallenge() throws MalformedURLException,IOException
{
	String str;
	LogArea.append("获取验证码中....\n");
	LogArea.selectAll();
	GetNetInfo getinfo=new GetNetInfo();
	String strmd5=version+clientip+Nasip+mac+timestamp+secret;
	 authenticator=getinfo.MD5(strmd5);
	JSONObject json=new JSONObject();
	StringBuilder jsonstr = new StringBuilder();  
	URL url=new URL("http://enet.10000.gd.cn:10001/client/vchallenge");
	URLConnection uc=url.openConnection();
	System.out.println("vchallenge"+" "+version+" "+clientip+" "+Nasip+" "+iswifiPC);
	json.put("version", "214");
	json.put("username", UserName);
	json.put("clientip", clientip);
	json.put("nasip", Nasip);
	json.put("mac", mac);
	json.put("iswifiPC", iswifiPC);
	json.put("timestamp", timestamp);
	json.put("authenticator", authenticator);
	uc.setDoOutput(true);
    uc.setDoInput(true);
    DataOutputStream out = new DataOutputStream(uc.getOutputStream());
out.writeBytes(json.toString());
    out.flush();
    uc.connect();
 InputStream is=uc.getInputStream();
    InputStreamReader ir=new InputStreamReader(is);
    BufferedReader br=new BufferedReader(ir);
   while((str=br.readLine())!=null)
    {
    	jsonstr.append(str);  	
    }
   
  System.out.println("vchallenge:"+jsonstr);
   str=jsonstr.toString();
String s[]=str.split("\"");
if(s[3]!=null)
verifyCodeString=s[3];
   br.close();
 if(!(verifyCodeString.equals("-1")))
 {LogArea.append("获取验证码成功\n");
 LogArea.selectAll();
	 //System.out.println("获取验证码成功");
 }
 else
 {LogArea.append("获取验证码失败\n");
 LogArea.selectAll();
	 
 }
 }
//登录
public void Login() throws Exception{
	
	String str;
	System.out.println(mac+timestamp);
	LogArea.append("登录中....\n");
	GetNetInfo getinfo=new GetNetInfo();
	String strmd5=clientip+Nasip+mac+timestamp+verifyCodeString+secret;
	 authenticator=getinfo.MD5(strmd5);
	JSONObject json=new JSONObject();
	StringBuilder jsonstr = new StringBuilder();  
	URL url=new URL("http://enet.10000.gd.cn:10001/client/login");
	URLConnection uc=url.openConnection();
	json.put("username", UserName);
	json.put("password", Password);
	json.put("clientip", clientip);
	json.put("nasip", Nasip);
	json.put("mac", mac);
	
	json.put("timestamp", timestamp);
	json.put("authenticator", authenticator);
	json.put("iswifiPC", iswifiPC);
	uc.setDoOutput(true);
    uc.setDoInput(true);  
    DataOutputStream out = new DataOutputStream(uc.getOutputStream());
out.writeBytes(json.toString());
    out.flush();
    uc.connect();
	
 InputStream is=uc.getInputStream();
    InputStreamReader ir=new InputStreamReader(is,"utf-8");
    BufferedReader br=new BufferedReader(ir);
   while((str=br.readLine())!=null)
    {
    	jsonstr.append(str);  
    	
    }
   System.out.println(jsonstr);
   br.close();
   str=jsonstr.toString();
   String s[]=str.split("\"");

   if(s[3]!=null)
	   result=s[3];
   else
	   result="";
   if(result.equals("0"))
	   {LogArea.append("登录成功\n");
	   p.WriteCach(clientip,Nasip, timestamp, "1");
	   p.Write(UserName, Password, RemeberBoolean);
	   login.setText("断开");
	   }
   else
	   LogArea.append("登录失败\n");
   LogArea.selectAll();
}
public static String getTimestamp() {
	return timestamp;
}
//断开
public void Logout() throws Exception{
	p.Read();
	String lastnasip="";
	String lasttime="";
	String lastclientip="";
	lastnasip=p.getLastNasip();
	lasttime=p.getLasttimes();
	lastclientip=p.getLastclientip();
	String str;
	GetNetInfo getinfo=new GetNetInfo();
	String strmd5=clientip+lastnasip+mac+timestamp+secret;
	 authenticator=getinfo.MD5(strmd5);
	JSONObject json=new JSONObject();
	StringBuilder jsonstr = new StringBuilder();  
	URL url=new URL("http://enet.10000.gd.cn:10001/client/logout");
	URLConnection uc=url.openConnection();
	json.put("clientip", lastclientip);
	json.put("nasip", lastnasip);
	json.put("mac", mac);
	json.put("timestamp", timestamp);
	json.put("authenticator", authenticator);
	uc.setDoOutput(true);
    uc.setDoInput(true);  
    DataOutputStream out = new DataOutputStream(uc.getOutputStream());
out.writeBytes(json.toString());
    out.flush();
    uc.connect();
	
 InputStream is=uc.getInputStream();
    InputStreamReader ir=new InputStreamReader(is,"utf-8");
    BufferedReader br=new BufferedReader(ir);
   while((str=br.readLine())!=null)
    {
    	jsonstr.append(str);  
    	
    }
   System.out.println(jsonstr);
   br.close();
  
LogArea.append("已断开\n");
	   p.WriteCach("","", "", "0");
	   login.setText("登录");
 
}
//续一秒活动
public void active() throws MalformedURLException, IOException,Exception{
	int activeTimeInt = Integer.parseInt(activeTime);
	TimerTask timerTask = new TimerTask() {
	String activeString;
	JSONObject jsonObject;
	String rescode;
	@Override
	public void run() {
		try{
	activeString = EsufingServiceConnect.this.keepConnection();
	jsonObject = new JSONObject(activeString);
	rescode = (String) jsonObject.opt("rescode");
	if ("0".equals(rescode)) {
		LogArea.append("成功续一秒\n");
	} else {
		String resinfo = jsonObject.optString("resinfo");
		LogArea.append("续一秒失败\n");
	verificate();
	vchallenge();
	Login();
	LogArea.selectAll();
	}	}
		catch(Exception e)
		{
			LogArea.append("续一秒错误\n");
			LogArea.selectAll();
		}
	}
};
Timer timer = new Timer();
	timer.schedule(timerTask, activeTimeInt * 60000, activeTimeInt * 60000);
}
//保持膜
private String keepConnection() throws Exception {
	p.Read();
	String nasip=p.getLastNasip();
	String lastclientip=p.getLastclientip();
	timestamp = System.currentTimeMillis() + "";
	authenticator = MD5Util.MD5(lastclientip + nasip + mac + timestamp + secret);
	String url = "http://enet.10000.gd.cn:8001/hbservice/client/active";

	String param = "username=" + UserName + "&clientip=" + lastclientip + "&nasip=" + nasip + "&mac=" + mac + "&timestamp=" + timestamp + "&authenticator="
			+ authenticator;
	String activeString = HttpUtil.doGet(url, param);
	return activeString;
}

public String getResult() {
	return result;
}
//登录结果
private void status(String str)
{
	if(str.equals("11010000"))
	{
		JOptionPane.showMessageDialog(login,"获取到的IP信息错误");
		LogArea.append("获取到的IP信息错误\n");
	}
	if(str.equals("13001000"))
	{
		JOptionPane.showMessageDialog(login,"认证请求被拒绝。");
		LogArea.append("认证请求被拒绝\n");
	}
	if(str.equals("13003000")){
		JOptionPane.showMessageDialog(login,"认证服务器忙，请稍后再试。");
		LogArea.append("认证服务器忙，请稍后再试\n");
	}
	if(str.equals("13005000")){
		JOptionPane.showMessageDialog(login,"请求认证超时，请检查网络后重试。");
		LogArea.append("请求认证超时，请检查网络后重试\n");
	}
	if(str.equals("13012000")){
		JOptionPane.showMessageDialog(login,"帐号或密码错误。");
		LogArea.append("帐号或密码错误\n");
	}
	if(str.equals("13015000")){
			JOptionPane.showMessageDialog(login,"账户余额不足。");
			LogArea.append("账户余额不足\n");
	}
		if(str.equals("13016000")){
			JOptionPane.showMessageDialog(login,"输入的帐号不存在，注意帐号无需@域名");
			LogArea.append("输入的帐号不存在，注意帐号无需@域名\n");
		}
		if(str.equals("13017000")){
			JOptionPane.showMessageDialog(login,"帐号状态异常，请咨询校园营业厅。");
			LogArea.append("帐号状态异常，请咨询校园营业厅\n");
		}
		if(str.equals("13018000")){
			JOptionPane.showMessageDialog(login,"拨号类型错误。");
			LogArea.append("拨号类型错误\n");
		}
		if(str.equals("20010001")){
			JOptionPane.showMessageDialog(login,"与服务器通信异常，请重新连接。");
			LogArea.append("与服务器通信异常，请重新连接\n");
		}
		if(str.equals("20010002")){
			JOptionPane.showMessageDialog(login,"IP不合法。");
			LogArea.append("IP不合法\n");
		}
		if(str.equals("20010006")){
			JOptionPane.showMessageDialog(login,"获取认证参数失败。");	
			LogArea.append("获取认证参数失败\n");
		}
		LogArea.selectAll();
}
@Override
public void run() {
	try {
	System.out.println(clientip.indexOf("192.168"));
	int ipreuslt=clientip.indexOf("10.100");
			if(ipreuslt==0)
			{
				verificate();
		vchallenge();	
			Login();
			active();
			status(result);}
			else
				{LogArea.append("IP不合法\n");
				}
		
	
			
	
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	catch(NullPointerException e){
		LogArea.append("登录异常\n");
		
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
