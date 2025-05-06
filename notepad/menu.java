package notepad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class menu  implements ActionListener
{
  JMenuBar menu1;
  
 public JMenu edit,view,zoom;
  JMenuItem newfile, open, save, exit;
  JMenuItem undo,cut,copy,paste,select;
  JMenuItem zoomin,zoomout,darkMode;
  JCheckBoxMenuItem statusbar,wordwrap; 
  main mainWindow; // Reference to main window

  public menu(main m) 
  {
      this.mainWindow = m; // Get reference of main window
  }
 public menu(menu menu) {
	// TODO Auto-generated constructor stub
}
public JMenuBar menubar()
  {
	//add menubar button
      menu1=new JMenuBar();
      
      JMenu file = new JMenu("File");
      newfile=new JMenuItem("New tab");
      newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));//shortcut key ke liye
      open=new JMenuItem("Open");
      open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
      save=new JMenuItem("Save");
      save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
      exit=new JMenuItem("Exit");
      exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
      
      edit=new JMenu("Edit");
      undo=new JMenuItem("Undo          Ctrl+Z");
      cut =new JMenuItem("Cut           Ctrl+X");
      copy=new JMenuItem("Copy          Ctrl+C");
      paste=new JMenuItem("Paste         Ctrl+V");
      select=new JMenuItem("Select All    Ctrl+A");
      
      view=new JMenu("View");
      darkMode = new JMenuItem("Dark Mode");
      zoom=new JMenu("Zoom");
      zoomin = new JMenuItem("Zoom In");
      zoomout = new JMenuItem("Zoom Out");

      statusbar=new JCheckBoxMenuItem("Status Bar",true);
      wordwrap=new JCheckBoxMenuItem("Word Wrap",true);
      
      menu1.add(file);
 
      file.add(newfile);
      file.add(open);
      file.add(save);
      file.add(exit);
      
      menu1.add(edit);
      edit.add(undo);
      edit.add(cut);
      edit.add(copy);
      edit.add(paste);
      edit.add(select);
      
      menu1.add(view);
      view.add(darkMode); 
      view.add(zoom);
      zoom.add(zoomin);
      zoom.add(zoomout);
      
      view.add(statusbar);
      view.add(wordwrap);
     
      file.addActionListener(this);
      
      newfile.addActionListener(this);
      open.addActionListener(this);
      save.addActionListener(this);
      exit.addActionListener(this);
      
      edit.addActionListener(this);
      undo.addActionListener(this);
      cut.addActionListener(this);
      copy.addActionListener(this);
      paste.addActionListener(this);
      select.addActionListener(this);
      
      view.addActionListener(this);
      
      darkMode.addActionListener(this);
      zoomin.addActionListener(this);
      zoomout.addActionListener(this);
      statusbar.addActionListener(this);
      wordwrap.addActionListener(this);
	
	return menu1;
    } 
  @Override
  public void actionPerformed(ActionEvent e) 
  {
	  //opening file
	  if (e.getSource() == open) 
	  {
		    JFileChooser fileChooser = new JFileChooser();
		    int result = fileChooser.showOpenDialog(mainWindow);
		    if (result == JFileChooser.APPROVE_OPTION) 
		    {
		    	  if (mainWindow.tab.getTabCount() == 0) 
		    	  {
		              mainWindow.createNewFile();
		          }
		        File selectedFile = fileChooser.getSelectedFile();
		        try {
		            BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
		            JTextArea activeTextArea = mainWindow.getActiveTextArea();
		            activeTextArea.read(reader, null);
		            reader.close();
		            // Set tab name as file name
		            mainWindow.updateCurrentTabTitle(selectedFile.getName());
    
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		}
	  //save file
	  if (e.getSource() == save) {
		    JFileChooser fileChooser = new JFileChooser();
		    int result = fileChooser.showSaveDialog(mainWindow);
		    if (result == JFileChooser.APPROVE_OPTION) {
		        File selectedFile = fileChooser.getSelectedFile();
		        try {
		            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));
		            JTextArea activeTextArea = mainWindow.getActiveTextArea();
		            activeTextArea.write(writer);
		            writer.close();
		            // Set tab name as file name
		            mainWindow.updateCurrentTabTitle(selectedFile.getName());
		        } catch (Exception ex) {
		            ex.printStackTrace();
		        }
		    }
		}
   //exit app
	  if (e.getSource() == exit) {
		    System.exit(0);
		}


	  // Undo MenuItem
	    if (e.getSource() == undo)
	    {
	        if (mainWindow.undoManager.canUndo()) 
	        {
	            mainWindow.undoManager.undo();
	        }
	    }

	    //Cut MenuItem
	    if (e.getSource() == cut) 
	    {
	        JTextArea activeTextArea = mainWindow.getActiveTextArea();
	        if (activeTextArea != null) 
	        {
	            activeTextArea.cut();
	        }
	    }

	    // Copy MenuItem 
	    if (e.getSource() == copy)
	    {
	        JTextArea activeTextArea = mainWindow.getActiveTextArea();
	        if (activeTextArea != null) 
	        {
	            activeTextArea.copy();
	        }
	    }

	    // Paste MenuItem 
	    if (e.getSource() == paste)
	    {
	        JTextArea activeTextArea = mainWindow.getActiveTextArea();
	        if (activeTextArea != null)
	        {
	            activeTextArea.paste();
	        }
	    }

	    // Select All MenuItem 
	    if (e.getSource() == select) 
	    {
	        JTextArea activeTextArea = mainWindow.getActiveTextArea();
	        if (activeTextArea != null)
	        {
	            activeTextArea.selectAll();
	        }
	    }

	    //exit
	    if (e.getSource() == exit)
	    {
	        System.exit(0);
	    }
	    
	  if (e.getSource() == newfile) 
	  {
          mainWindow.createNewFile(); // Call createNewFile method
      }
	  //dark mode 
	  if(e.getSource()== darkMode)
	  {
		mainWindow.toggleDarkMode(mainWindow);

	  }
	  //zoomin our zoom out event perform
	  if (e.getSource() == zoomin) 
	  {
          mainWindow.zoomIn(); //method call zoom in
      }
      if (e.getSource() == zoomout) 
      {
          mainWindow.zoomOut(); // Call method
      }
	  //statusbar working code
	  if (e.getSource() == statusbar)
	   {
          if (statusbar.isSelected()) 
            {
        	  mainWindow.statusBar.setVisible(true); // Show
            }
          else 
            {
        	  mainWindow.statusBar.setVisible(false); // Hide
            }
        }
	  //wordwrap working code

	  if (e.getSource() == wordwrap) 
	  {
	      JTextArea activeTextArea = mainWindow.getActiveTextArea();
	      if (activeTextArea != null)
	      {
	          if (wordwrap.isSelected()) 
	          {
	              activeTextArea.setLineWrap(true);
	              activeTextArea.setWrapStyleWord(true);
	          } else
	          {
	              activeTextArea.setLineWrap(false);
	              activeTextArea.setWrapStyleWord(false);
	          }
	      }
	  }
  }
}
