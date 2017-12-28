import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import javax.swing.UIManager;

import com.sun.awt.AWTUtilities;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class main extends javax.swing.JFrame {
	private JLabel UserLabel;
	private JTextField UserText;
	private JTextArea jTextArea1;
	private static JCheckBox Advance;
	private JScrollPane jScrollPane1;
	private JTextArea LogArea;
	private JCheckBox Mix;
	private JCheckBox Remembercheck;
	JButton login;
	JScrollBar jsb;
	private JFormattedTextField PasswordText;
	private JLabel passwordLabel;
	ByteArrayOutputStream connectStream;
	PrintStream oldStream = System.out;
	static String UserName="";
	static String Password="";
	static String Remember="";
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    public int height = Toolkit.getDefaultToolkit().getScreenSize().height;
	static PropertiesUtils p=new PropertiesUtils();
	  TrayIcon trayIcon = null;
	  String IsWifiStatus="";
	  String IPGetStatus="";
	  String RemeberBoolean="";
	  SystemTray tray = SystemTray.getSystemTray(); 
	  static Option op;
	/**
	* Auto-generated main method to display this JDialog
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
					} catch (Exception e) {
					e.printStackTrace();
					}
			
			     try{
				  UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");    
			     }catch(Exception e){}
			 
			     
				try {
					
					p.Read();	
					
					Remember=p.getRemember();
					if((p.getRemember())!=null)
					if(Remember.equals("记住我"))
					{
						UserName=p.getUserName();
				Password=p.getPassWord();
				}
					else
					{
						p.Write("", "", "");
						Remember="0";
					}
				} catch (IOException e) {
				
					try {
						p.Write("", "", "");
						p.WriteOption("-1", "-1", "-1", "-1");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				

				JFrame frame = new JFrame();
				main inst = new main(frame);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				frame.setUndecorated(true); 
		
			}
		});
		
	}
	
	 
	public main(JFrame frame) {
		
		super();
		 
		initGUI();
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	
		 if (SystemTray.isSupported()) // 判断系统是否支持系统托盘
		  {
		  // 创建系统托盘
		   Image image = Toolkit.getDefaultToolkit().getImage("2.png");// 载入图片,这里要写你的图标路径哦
		 
		   ActionListener listener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {

		    setVisible(true);
		    }
		    
		   };
		   // 创建弹出菜单
		   PopupMenu popup = new PopupMenu();
		   //主界面选项
		   MenuItem mainFrameItem = new MenuItem("主界面");
		   mainFrameItem.addActionListener(listener);
		   
		 //退出程序选项
		   MenuItem exitItem = new MenuItem("退出");
		   exitItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		     if (JOptionPane.showConfirmDialog(null, "退出可能断开连接,\n              确定退出") == 0) {
		      System.exit(0);
		      
		     }
		    }
		   });
		   
		   popup.add(mainFrameItem);
		   popup.add(exitItem);
		   
		   trayIcon = new TrayIcon(image, "天翼", popup);// 创建trayIcon
		   trayIcon.addActionListener(listener);
		   try {
		    tray.add(trayIcon);
		   } catch (AWTException e1) {
		    e1.printStackTrace();
		   }
		  }
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			EsufingServiceConnect e1;
			try {
				e1 = new EsufingServiceConnect(UserName,Password,login,IPGetStatus,LogArea,RemeberBoolean);
			    if (JOptionPane.showConfirmDialog(null, "退出可能断开连接,\n              确定退出") == 0) 
			     {
			      System.exit(0);
			      e1.Logout();
			     
			     }
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (UnknownHostException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SocketException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
				 
				    
			
		}
		public void windowIconified(WindowEvent e) {  
			  dispose();
	    }  
		});
		
	}
	
	private void initGUI() {
		try {
			{
				getContentPane().setLayout(null);
				{
					UserLabel = new JLabel();
					getContentPane().add(UserLabel);
					UserLabel.setText("\u8d26\u53f7:");
					UserLabel.setBounds(102, 83, 45, 27);
				}
				{
					UserText = new JTextField();
					getContentPane().add(UserText);
					UserText.setBounds(147, 83, 140, 24);
					UserText.setText(UserName);
					UserText.setToolTipText("\u7528\u6237\u540d");
				}
				{
					passwordLabel = new JLabel();
					getContentPane().add(passwordLabel);
					passwordLabel.setText("\u5bc6\u7801:");
					passwordLabel.setBounds(102, 127, 45, 21);
				}
				{
					PasswordText = new JFormattedTextField();
					getContentPane().add(PasswordText);
					PasswordText.setBounds(147, 124, 140, 24);
					PasswordText.setText(Password);
				}
				{
					login = new JButton();
					getContentPane().add(login);
					login.setText("\u767b\u5f55");
					login.setBounds(120, 194, 147, 36);
					login.setBackground(new java.awt.Color(0,128,255));
					login.setForeground(new java.awt.Color(255,255,255));
					login.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							try {
								loginMouseClicked(evt);
							} catch (MalformedURLException e) {
								System.out.println("获取nasip失败");
								LogArea.append("获取nasip失败\n");
								e.printStackTrace();
							} catch (UnknownHostException e1) {
									JOptionPane.showMessageDialog(login, "网络未连接");
								LogArea.append("网络未连接\n");
								System.exit(0);
							
							} catch (SocketException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							catch(Exception e){
								e.printStackTrace();
								
							}
						}
					});
				}
				{
					
					Remembercheck = new JCheckBox();
					getContentPane().add(Remembercheck);
					Remembercheck.setText("\u8bb0\u4f4f\u6211");
					Remembercheck.setBounds(126, 162, 68, 23);
					Remembercheck.setBackground(new java.awt.Color(219,237,255));
					Remembercheck.setForeground(new java.awt.Color(0,0,0));
					if(Remember!=null)
					if(Remember.equals("记住我"))
						Remembercheck.setSelected(true);
				}
				{
					Mix = new JCheckBox();
					getContentPane().add(Mix);
					Mix.setText("\u517c\u5bb9\u6a21\u5f0f");
					Mix.setBounds(197, 113, 99, 33);
					Mix.setFont(new java.awt.Font("宋体",0,12));
					Mix.setVisible(false);
				}
				{
					jScrollPane1 = new JScrollPane();
					getContentPane().add(jScrollPane1);
					jScrollPane1.setBounds(1, 236, 383, 125);
					jScrollPane1.setForeground(new java.awt.Color(255,255,255));
					{
						LogArea = new JTextArea();
						jScrollPane1.setViewportView(LogArea);
						LogArea.setBounds(13, 152, 380, 122);
						LogArea.setEditable(false);
					
						LogArea.setBorder(new LineBorder(new java.awt.Color(0,0,0), 1, false));
						LogArea.setBackground(Color.white);
						LogArea.setForeground(new java.awt.Color(0,0,0));

					}
					jScrollPane1.setVisible(false);
					jsb=jScrollPane1.getVerticalScrollBar();
					jsb.setValue(jsb.getMaximum());
				}
				{
					Advance = new JCheckBox();
					getContentPane().add(Advance);
					Advance.setText("\u9ad8\u7ea7\u9009\u9879");
					Advance.setBounds(205, 162, 77, 22);
					Advance.setBackground(new java.awt.Color(217,236,255));
					Advance.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent evt) {
							AdvanceMouseClicked(evt);
						}
					});
				}
				{
					jTextArea1 = new JTextArea();
					getContentPane().add(jTextArea1);
					jTextArea1.setText("\n                  Esufing");
					jTextArea1.setBounds(-1, -10, 396, 81);
					jTextArea1.setBackground(new java.awt.Color(0,128,255));
					jTextArea1.setFont(new java.awt.Font("Microsoft YaHei UI",1,28));
					jTextArea1.setForeground(new java.awt.Color(255,255,255));
					jTextArea1.setEditable(false);
					jTextArea1.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, new java.awt.Color(0,128,255), new java.awt.Color(0,128,255), null, null));
					
				}
			}
			setSize(400, 300);
			this.setBounds(0, 0, 400, 300);
			setTitle("Esufing_By超威黑猫");
			getContentPane().setBackground(new java.awt.Color(227,241,255));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loginMouseClicked(MouseEvent evt) throws Exception {
		setSize(400, 400);
		
	jScrollPane1.setVisible(true);
		
		UserName=UserText.getText();
		Password=PasswordText.getText();
		if(Mix.isSelected())
			IPGetStatus="2";
		else
			IPGetStatus="1";
		if(Remembercheck.isSelected())
			RemeberBoolean="记住我";
		else
			RemeberBoolean="";
		EsufingServiceConnect e = new EsufingServiceConnect(UserName,Password,login,IPGetStatus,LogArea,RemeberBoolean);
		if(login.getText().equals("登录"))
		{
		
		
		Thread thread = new Thread(e);   
		thread.start(); 


		}
		else
		{
			
		e.Logout();
		}
		
	}
	
	private void AdvanceMouseClicked(MouseEvent evt) {
		System.out.println("Advance.mouseClicked, event="+evt);
	if(Advance.isSelected()){
		
		op=new Option(null,p,Advance);
		op.setVisible(true);
		op.setBounds((width - 400) / 2,
                (height - 300) / 2, 391, 166);
		
	}
	else
		op.dispose();
		
		
	}

}
