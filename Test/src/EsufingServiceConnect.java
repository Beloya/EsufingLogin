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
	//�ж��Ƿ��Զ����ȡnasip
	
	if(!(p.getGetNasipType().equals("1")))
	Nasip=getinfo.getNasip();
	else
		Nasip=p.getNasip();
	//�жϻ�ȡip��ʽ
	
	if(p.getIPGetStatus().toString().equals("����"))
		clientip=getinfo.getLocattionIP();//����·��������
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
	//�жϵ�¼ģ����ն�
	if(p.getIsWifiStatus().equals("�ֻ�"))
			iswifiPC="4060";
	else
		iswifiPC="1060";
	
}
public static String getNasip() {
	return Nasip;
}
//��֤��Ϣ
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
//��ս��Ϣ��ȡ
public static void vchallenge() throws MalformedURLException,IOException
{
	String str;
	LogArea.append("��ȡ��֤����....\n");
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
 {LogArea.append("��ȡ��֤��ɹ�\n");
 LogArea.selectAll();
	 //System.out.println("��ȡ��֤��ɹ�");
 }
 else
 {LogArea.append("��ȡ��֤��ʧ��\n");
 LogArea.selectAll();
	 
 }
 }
//��¼
public void Login() throws Exception{
	
	String str;
	System.out.println(mac+timestamp);
	LogArea.append("��¼��....\n");
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
	   {LogArea.append("��¼�ɹ�\n");
	   p.WriteCach(clientip,Nasip, timestamp, "1");
	   p.Write(UserName, Password, RemeberBoolean);
	   login.setText("�Ͽ�");
	   }
   else
	   LogArea.append("��¼ʧ��\n");
   LogArea.selectAll();
}
public static String getTimestamp() {
	return timestamp;
}
//�Ͽ�
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
  
LogArea.append("�ѶϿ�\n");
	   p.WriteCach("","", "", "0");
	   login.setText("��¼");
 
}
//��һ��
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
		LogArea.append("�ɹ���һ��\n");
	} else {
		String resinfo = jsonObject.optString("resinfo");
		LogArea.append("��һ��ʧ��\n");
	verificate();
	vchallenge();
	Login();
	LogArea.selectAll();
	}	}
		catch(Exception e)
		{
			LogArea.append("��һ�����\n");
			LogArea.selectAll();
		}
	}
};
Timer timer = new Timer();
	timer.schedule(timerTask, activeTimeInt * 60000, activeTimeInt * 60000);
}
//����Ĥ
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
//��¼���
private void status(String str)
{
	if(str.equals("11010000"))
	{
		JOptionPane.showMessageDialog(login,"��ȡ����IP��Ϣ����");
		LogArea.append("��ȡ����IP��Ϣ����\n");
	}
	if(str.equals("13001000"))
	{
		JOptionPane.showMessageDialog(login,"��֤���󱻾ܾ���");
		LogArea.append("��֤���󱻾ܾ�\n");
	}
	if(str.equals("13003000")){
		JOptionPane.showMessageDialog(login,"��֤������æ�����Ժ����ԡ�");
		LogArea.append("��֤������æ�����Ժ�����\n");
	}
	if(str.equals("13005000")){
		JOptionPane.showMessageDialog(login,"������֤��ʱ��������������ԡ�");
		LogArea.append("������֤��ʱ���������������\n");
	}
	if(str.equals("13012000")){
		JOptionPane.showMessageDialog(login,"�ʺŻ��������");
		LogArea.append("�ʺŻ��������\n");
	}
	if(str.equals("13015000")){
			JOptionPane.showMessageDialog(login,"�˻����㡣");
			LogArea.append("�˻�����\n");
	}
		if(str.equals("13016000")){
			JOptionPane.showMessageDialog(login,"������ʺŲ����ڣ�ע���ʺ�����@����");
			LogArea.append("������ʺŲ����ڣ�ע���ʺ�����@����\n");
		}
		if(str.equals("13017000")){
			JOptionPane.showMessageDialog(login,"�ʺ�״̬�쳣������ѯУ԰Ӫҵ����");
			LogArea.append("�ʺ�״̬�쳣������ѯУ԰Ӫҵ��\n");
		}
		if(str.equals("13018000")){
			JOptionPane.showMessageDialog(login,"�������ʹ���");
			LogArea.append("�������ʹ���\n");
		}
		if(str.equals("20010001")){
			JOptionPane.showMessageDialog(login,"�������ͨ���쳣�����������ӡ�");
			LogArea.append("�������ͨ���쳣������������\n");
		}
		if(str.equals("20010002")){
			JOptionPane.showMessageDialog(login,"IP���Ϸ���");
			LogArea.append("IP���Ϸ�\n");
		}
		if(str.equals("20010006")){
			JOptionPane.showMessageDialog(login,"��ȡ��֤����ʧ�ܡ�");	
			LogArea.append("��ȡ��֤����ʧ��\n");
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
				{LogArea.append("IP���Ϸ�\n");
				}
		
	
			
	
	} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	catch(NullPointerException e){
		LogArea.append("��¼�쳣\n");
		
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}
