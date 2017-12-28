import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


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
public class Option extends javax.swing.JDialog {
	private JLabel jLabel1;
	private JComboBox IPGetType;
	private JLabel jLabel2;
	private JComboBox IsWifi;
	private JTextField NasipInput;
	private JCheckBox GetNasip;
	private JButton Save;
	String IsWifiSelect;
	String IPGetTypeSelect;
	String NasipIsauoto;
	String Nasiptext;
	static JCheckBox Advance;
	static PropertiesUtils p=new PropertiesUtils();
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
				JFrame frame = new JFrame();
				Option inst = new Option(frame, p,Advance);
				inst.setVisible(true);
			}
		});
	}
	
	public Option(JFrame frame,PropertiesUtils p,final JCheckBox Advance) {
	
		super(frame);
		
		initGUI();
		this.Advance=Advance;
		try {
			p.Read();
			if((p.getIPGetStatus())!=null)
			if(p.getIPGetStatus().equals("特殊"))
				IPGetType.setSelectedIndex(1);
			else
				IPGetType.setSelectedIndex(0);
			if((p.getIsWifiStatus())!=null)
			if(p.getIsWifiStatus().equals("手机"))
				IsWifi.setSelectedIndex(1);
			else
				IsWifi.setSelectedIndex(0);
			if((p.getGetNasipType())!=null)
			if(p.GetNasipType.equals("1"))
			{
				GetNasip.setSelected(true);
				NasipInput.setVisible(true);
				NasipInput.setText(p.getNasip());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Advance.setSelected(false);
				
			}
			});
	
	
	
	}
	
	private void initGUI() {
		try {
			this.setTitle("高级选项");
			getContentPane().setLayout(null);
			{
				jLabel1 = new JLabel();
				getContentPane().add(jLabel1);
				jLabel1.setText("\u6a21\u5f0f\u9009\u62e9");
				jLabel1.setBounds(47, 31, 73, 19);
			}
			{
				ComboBoxModel IPGetTypeModel = 
					new DefaultComboBoxModel(
							new String[] { "正常", "特殊" });
				IPGetType = new JComboBox();
				getContentPane().add(IPGetType);
				IPGetType.setModel(IPGetTypeModel);
				IPGetType.setBounds(138, 29, 119, 22);
				IPGetType.setBackground(Color.white);
			}
			{
				jLabel2 = new JLabel();
				getContentPane().add(jLabel2);
				jLabel2.setText("\u6a21\u62df\u7ec8\u7aef");
				jLabel2.setBounds(47, 68, 56, 19);
			}
			{
				ComboBoxModel IsWifiModel = 
					new DefaultComboBoxModel(
							new String[] { "PC", "手机" });
				IsWifi = new JComboBox();
				getContentPane().add(IsWifi);
				IsWifi.setModel(IsWifiModel);
				IsWifi.setBounds(138, 66, 119, 26);
				IsWifi.setBackground(Color.white);
			}
			{
				Save = new JButton();
				getContentPane().add(Save);
				Save.setText("\u4fdd\u5b58");
				Save.setBounds(273, 38, 72, 44);
				Save.setBackground(Color.BLACK);
				Save.setForeground(new java.awt.Color(255,255,255));
				Save.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						SaveMouseClicked(evt);
					}
				});

			}
			{
				GetNasip = new JCheckBox();
				getContentPane().add(GetNasip);
				GetNasip.setText("\u81ea\u5b9a\u4e49Nasip");
				GetNasip.setBounds(20, 95, 103, 25);
				GetNasip.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent evt) {
						GetNasipMouseClicked(evt);
					}
				});
			}
			{
				NasipInput = new JTextField();
				getContentPane().add(NasipInput);
				NasipInput.setBounds(138, 98, 119, 22);
				NasipInput.setVisible(false);
			}
			this.setSize(397, 171);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void GetNasipMouseClicked(MouseEvent evt) {
		if(GetNasip.isSelected())
		{NasipInput.setVisible(true);}
		else
			NasipInput.setVisible(false);
	}
	
	private void SaveMouseClicked(MouseEvent evt) {
		IPGetTypeSelect=IPGetType.getSelectedItem().toString();
		IsWifiSelect=IsWifi.getSelectedItem().toString();
		if(GetNasip.isSelected())
			{Nasiptext=NasipInput.getText();
			NasipIsauoto="1";
			
			}
		else
			NasipIsauoto="0";
	try {
		if(NasipIsauoto.equals("1"))
		if(!(NasipInput.getText().isEmpty()))
		{p.WriteOption(IsWifiSelect, IPGetTypeSelect,Nasiptext , NasipIsauoto);
		dispose();
		}
		else
			{JOptionPane.showMessageDialog(Save, "请输入Nasip");
			NasipInput.setVisible(true);
			}
		else
			{p.WriteOption(IsWifiSelect, IPGetTypeSelect,Nasiptext , NasipIsauoto);
			
		this.dispose();
				
			}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Advance.setSelected(false);
	}

}
