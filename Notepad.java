import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class Notepad implements ActionListener{
	JFrame jf;
	JMenuBar menubar;
	JMenu file, edit, format, help;
	JMenuItem neww, open, save , saveas, exit, cut, copy, paste, selectall, fontcolor, bgcolor, choosefont;
	JTextArea textarea;
	JScrollPane scrollpane;
	String default_title = "Untitled - Notepad";
	File saveFile;
	JButton ok, cancel;
	JComboBox fontfamily, fontstyle, fontsize;
	JFrame fontframe;
	Notepad(){
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		}catch(Exception e){
			System.out.println(e);
		}
		jf = new JFrame(default_title);
		jf.setSize(1100,500);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(3);
		
		menubar = new JMenuBar();
		
		file = new JMenu("File");
		
		neww = new JMenuItem("New                          Ctrl+N");
		neww.addActionListener(this);
		file.add(neww);
		
		open = new JMenuItem("Open                         Ctrl+O");
		open.addActionListener(this);
		file.add(open);
		
		save = new JMenuItem("Save                          Ctrl+S");
		save.addActionListener(this);
		file.add(save);
		
		saveas = new JMenuItem("Save  as             Ctrl+Shift+S");
		saveas.addActionListener(this);
		file.add(saveas);
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		menubar.add(file);
		
		edit = new JMenu("Edit");
		
		cut = new JMenuItem("Cut                     Ctrl+X");
		cut.addActionListener(this);
		edit.add(cut);
		
		copy = new JMenuItem("Copy                  Ctrl+C");
		copy.addActionListener(this);
		edit.add(copy);
		
		paste = new JMenuItem("Paste                  Ctrl+V");
		paste.addActionListener(this);
		edit.add(paste);
		
		selectall = new JMenuItem("Select All            Ctrl+A");
		selectall.addActionListener(this);
		edit.add(selectall);
		
		menubar.add(edit);
		
		format = new JMenu("Format");
		
		fontcolor = new JMenuItem("Font Color");
		fontcolor.addActionListener(this);
		format.add(fontcolor);
		
		bgcolor = new JMenuItem("Background Color");
		bgcolor.addActionListener(this);
		format.add(bgcolor);
		
		choosefont = new JMenuItem("Font");
		choosefont.addActionListener(this);
		format.add(choosefont);
		
		menubar.add(format);
		
		help = new JMenu("Help");
		help.addActionListener(this);
		menubar.add(help);
		
		jf.setJMenuBar(menubar);
		
		textarea = new JTextArea();
		scrollpane = new JScrollPane(textarea);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		jf.add(scrollpane);
		
		
		jf.setVisible(true);	
	}

	public static void main(String[] args) {
		

		new Notepad();
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == neww) {
			String title = jf.getTitle();
			String text = textarea.getText();
			if(!text.equals("")) {
				
				int i = JOptionPane.showConfirmDialog(open, "Do you want to save this file ?");
				if(i == 0) {
					saveFile();
					jf.setTitle(default_title);
					textarea.setText("");
				}else if(i == 1){
					textarea.setText("");
				}
				
			}
			
		}
		if(e.getSource() == open) {
			openFile();	
		}
		
		if(e.getSource() == save) {
			saveFile();		
		}
		
		if(e.getSource() == saveas) {
			saveAs();		
		}
		
		if(e.getSource() == exit) {
			System.exit(0);		
		}
		
		if(e.getSource() == cut) {
			textarea.cut();		
		}
		if(e.getSource() == copy) {
			textarea.copy();		
		}
		if(e.getSource() == paste) {
			textarea.paste();		
		}
		if(e.getSource() == selectall) {
			textarea.selectAll();		
		}
		
		
		if(e.getSource() == fontcolor) {
			setFontColor();	
		}
		if(e.getSource() == bgcolor) {
			setBackgroundColor();	
		}
		if(e.getSource() == choosefont) {
			openFontFrame();	
		}
		if(e.getSource() == ok) {
			setNewFontOnTextArea();	
		}
		
		
	}
			
			
	void openFile() {
		JFileChooser filechooser = new JFileChooser();
		int i = filechooser.showOpenDialog(jf);
		if(i==0) {
			File filee = filechooser.getSelectedFile();
			try {
				
				textarea.setText("");
				FileInputStream fis = new FileInputStream(filee);
				int ii;
				while((ii = fis.read()) != -1) {
					textarea.append(String.valueOf((char)ii));
				}
				
				
				jf.setTitle(filee.getName());
			}
			catch(Exception ee) {
				System.out.println(ee);
			}
		}
	}
	
	void saveFile() {
		String title = jf.getTitle();
		if(title.equals(default_title)) {
			saveAs();
		}
		else {
			try {
				String str = textarea.getText();
				FileOutputStream fos = new FileOutputStream(saveFile);
				fos.write(str.getBytes());
			
				
				
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		
	}
	
	void saveAs() {
		
		JFileChooser filechooser = new JFileChooser();
		int i = filechooser.showOpenDialog(jf);
		if(i==0) {
			try {
				saveFile = filechooser.getSelectedFile();
				String str = textarea.getText();
				FileOutputStream fos = new FileOutputStream(saveFile);
				fos.write(str.getBytes());
				
				
				jf.setTitle(saveFile.getName());
				
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}
		
	}
	
	void setFontColor() {
		Color c = JColorChooser.showDialog(jf, "Choose Font Color", Color.black);
		textarea.setForeground(c);
	}
	
	void setBackgroundColor() {
		Color c = JColorChooser.showDialog(open, "Choose Background Color", Color.white);
		textarea.setBackground(c);
	}
	void openFontFrame() {
		fontframe = new JFrame("Choose Font");
		fontframe.setSize(600,400);
		fontframe.setLocationRelativeTo(jf);
		fontframe.setLayout(null);
		
		String[] fontfamilyy = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		fontfamily = new JComboBox(fontfamilyy);
		fontfamily.setBounds(50, 50, 200, 40);
		fontframe.add(fontfamily);
		
		String[] stylee = {"Plain", "Bold", "Italic"};
		fontstyle = new JComboBox(stylee);
		fontstyle.setBounds(300, 50, 100, 40);
		fontframe.add(fontstyle);
		
		String[] sizee = {"10","16","20","30","40","50","60"};
		fontsize = new JComboBox(sizee);
		fontsize.setBounds(450, 50, 70, 40);
		fontframe.add(fontsize);
		
		ok = new JButton("Ok");
		ok.setBounds(100, 150, 80, 40);
		ok.addActionListener(this);
		fontframe.add(ok);
		
		cancel = new JButton("Cancle");
		cancel.setBounds(250, 150, 80, 40);
		fontframe.add(cancel);
		
		fontframe.setVisible(true);
	}
	
	void setNewFontOnTextArea() {
		String ffamily = (String)fontfamily.getSelectedItem();
		String fsize = (String)fontsize.getSelectedItem();
		String fstyle = (String)fontstyle.getSelectedItem();
		int fstylee = 0;
		if(fstyle.equals("Plain")) {
			fstylee = 0;
		}else if(fstyle.equals("Bold")) {
			fstylee = 1;
		}else if(fstyle.equals("Italic")) {
			fstylee = 2;
		}
		Font f = new Font(ffamily, fstylee, Integer.parseInt(fsize));
		textarea.setFont(f);
		fontframe.setVisible(false);
	}
		
	
	}


