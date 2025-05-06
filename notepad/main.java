package notepad;

import notepad.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Label;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.MenuElement;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.undo.UndoManager;
public class main extends JFrame implements ActionListener,KeyListener
{
	Label l1,l2;
	JTextArea t1;
    JScrollPane scroll;
    JLabel statusBar;
    int fontSize=16;
    JTabbedPane tab;
    UndoManager undoManager;
    JLabel tabTitle;
    main()
    {
    	setSize(2000,900);
        setTitle("Untitled - PK_Notepad");  // Set title to "Untitled"
        setDefaultCloseOperation(EXIT_ON_CLOSE);
   
        //new tab code
        tab=new JTabbedPane();     
        setJMenuBar(new menu(this).menubar());//menu.java calling
        
        menu myMenu = new menu(this);
        setJMenuBar(myMenu.menubar());

        //ctrl + Z use karne ke liye
        undoManager = new UndoManager();

        
        // Add initial empty tab
        addFile("Untitled");

        add(tab, BorderLayout.CENTER);

        statusBar = new JLabel("Ln 1, Col 1 | 0 characters | Windows (CRLF) | UTF-8");
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(statusBar, BorderLayout.SOUTH);
  	
        
        // Focusable set karo ctrl+ our - ke liye
        t1.setFocusable(true);
        // Add KeyListener
        t1.addKeyListener(this);
        
        //dark mode ke liye code
     
       

    
        setVisible(true);
    }
  private void addFile(String title) 
  {
	  t1=new JTextArea();
      t1.setFont(new Font("Arial", Font.PLAIN, fontSize));//by default font size 18 is font size
      t1.setLineWrap(true); //by default worldwrap true
      t1.setWrapStyleWord(true); //by default worldwrap true
     
      //addscrollbar
      scroll = new JScrollPane(t1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      scroll.setBorder(BorderFactory.createEmptyBorder());
      
      JPanel tabPanel = new JPanel(new BorderLayout());
      tabPanel.setOpaque(false);
       tabTitle = new JLabel(title);
      JButton close = new JButton("   x");
      close.setFocusable(false);
      close.setBorder(BorderFactory.createEmptyBorder());
      close.setContentAreaFilled(false);
      
      //file name ke aage ka button
      close.addActionListener(new ActionListener()
      {
          public void actionPerformed(ActionEvent e) 
          {
              int i = tab.indexOfComponent(scroll);
              if (i != -1)
              {
                  tab.remove(i);
              }
              if (tab.getTabCount() == 0)
              {
                  addFile("Untitled");
              }    
          }
      });
      
      tabPanel.add(tabTitle, BorderLayout.WEST);
      tabPanel.add(close, BorderLayout.EAST);

      tab.addTab(title, scroll);
      tab.setTabComponentAt(tab.indexOfComponent(scroll), tabPanel);
      tab.setSelectedComponent(scroll);

        
      
      //ctrl+Z ke liye
      t1.getDocument().addUndoableEditListener(undoManager);
      
      //new tab ke liye key listner
      t1.addKeyListener(this);
     
      // Add CaretListener to update line, column and characters
      t1.addCaretListener(new CaretListener()
      {
          public void caretUpdate(CaretEvent e) 
          {
              try {
                  int caretPos = t1.getCaretPosition();
                  int line = t1.getLineOfOffset(caretPos);
                  int column = caretPos - t1.getLineStartOffset(line);

                  statusBar.setText("Ln " + (line + 1) + ", Col " + (column + 1) +
                                    " | " + t1.getText().length() + " characters | Windows (CRLF) | UTF-8");
              } catch (Exception ex) {
                  ex.printStackTrace();
              }
          }
      });
	}
  
  public void updateCurrentTabTitle(String title) 
  {
	    int index = tab.getSelectedIndex();
	    if (index != -1) 
	    {
	        Component c = tab.getTabComponentAt(index);
	        if (c instanceof JPanel panel) {
	            for (Component comp : panel.getComponents()) 
	            {
	                if (comp instanceof JLabel label)
	                {
	                    label.setText(title);
	                    break;
	                }
	            }
	        }
	    }
	}

  //dark mode function
  private boolean isDarkMode = false;

  public void toggleDarkMode(main mainWindow) 
  {
      isDarkMode = !isDarkMode;

      Color bgColor = isDarkMode ? new Color(45, 45, 45) : Color.WHITE;
      Color fgColor = isDarkMode ? Color.WHITE : Color.BLACK;

      
      for (int i = 0; i < mainWindow.tab.getTabCount(); i++) 
      {
          JScrollPane scrollPane = (JScrollPane) mainWindow.tab.getComponentAt(i);
          JTextArea textArea = (JTextArea) scrollPane.getViewport().getView();
          textArea.setBackground(bgColor);
          textArea.setForeground(fgColor);
          textArea.setCaretColor(fgColor);
          
      //menu bar ke liye
          JMenuBar menuBar = mainWindow.getJMenuBar();
          if (menuBar != null)
          {
        	 

              menuBar.setBackground(bgColor);
              menuBar.setForeground(fgColor);
              for (MenuElement menuElement : menuBar.getSubElements())
              {
                  JMenu menu = (JMenu) menuElement.getComponent();
                  menu.setBackground(bgColor);
                  menu.setForeground(fgColor);
               
                  for (Component item : menu.getMenuComponents()) 
                  {
                      item.setBackground(bgColor);
                      item.setForeground(fgColor);
                  
                  }
              }
          }
      }

      // Tab aur StatusBar 
      mainWindow.tab.setBackground(bgColor);
      mainWindow.tab.setForeground(fgColor);
      mainWindow.statusBar.setBackground(bgColor);
      mainWindow.statusBar.setForeground(fgColor);

      // Window background 
      mainWindow.getContentPane().setBackground(bgColor);
  }
  @Override
  public void actionPerformed(ActionEvent e) 
    {	
	  int index = tab.indexOfComponent(scroll);
	    if (index != -1)
	    {
	        tab.remove(index);
	    }  	
    }

  //new tab
  public void createNewFile() {
    	addFile("Untitled");
          
    }
    //zoom in
    public void zoomIn() {
    	  fontSize += 2;
          JTextArea activeTextArea = getActiveTextArea();  
          if (activeTextArea != null)
          {
              activeTextArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
          }
    }
	// Zoom Out 
    public void zoomOut()
    {
    	    if (fontSize > 8) 
    	    {
    	        fontSize -= 2;
    	    }
    	    JTextArea activeTextArea = getActiveTextArea();
    	    if (activeTextArea != null)
    	    {
    	        activeTextArea.setFont(new Font("Arial", Font.PLAIN, fontSize));
    	    }
    }
    JTextArea getActiveTextArea() 
    {
        Component comp = tab.getSelectedComponent();
        if (comp instanceof JScrollPane scrollPane)
        {
            return (JTextArea) scrollPane.getViewport().getView();
        }
        return null;
    }

@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
@Override
public void keyPressed(KeyEvent e) 
{
	 if (e.isControlDown())
	 {
         // ctrl+ +
         if (e.getKeyCode() == KeyEvent.VK_EQUALS)
         {
             zoomIn(); 
         }
         // ctrl+ -
         else if (e.getKeyCode() == KeyEvent.VK_MINUS)
         {
             zoomOut(); 
         }
        // Undo (Ctrl+Z)
        else if (e.getKeyCode() == KeyEvent.VK_Z)
        {
        	 if (undoManager.canUndo()) 
        	 {
        	   undoManager.undo();
        	 }
        }
        // Cut (Ctrl+X)
       else if (e.getKeyCode() == KeyEvent.VK_X)
       {
         JTextArea activeTextArea = getActiveTextArea();
          if (activeTextArea != null)
          {
            activeTextArea.cut();
           }
       }
      // Copy (Ctrl+C)
     else if (e.getKeyCode() == KeyEvent.VK_C)
     {
       JTextArea activeTextArea = getActiveTextArea();
          if (activeTextArea != null)
          {
             activeTextArea.copy();
          }
      }
      // Paste (Ctrl+V)
       else if (e.getKeyCode() == KeyEvent.VK_V)
       {
         JTextArea activeTextArea = getActiveTextArea();
          if (activeTextArea != null) 
          {
            activeTextArea.paste();
          }
       }
      // Select All (Ctrl+A)
    else if (e.getKeyCode() == KeyEvent.VK_A)
    {
       JTextArea activeTextArea = getActiveTextArea();
         if (activeTextArea != null) {
         activeTextArea.selectAll();
                     }
                 }
             }
}
@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
public static void main(String[] args) 
{	
	 new main();

}
}