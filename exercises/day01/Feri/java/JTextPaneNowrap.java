import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledDocument;

public class JTextPaneNowrap extends JTextPane {
	
	   boolean wrap = false;
	   int maxWidth = 0;

	   public JTextPaneNowrap()
	   {
	   }

	   public JTextPaneNowrap(boolean wrap)
	   {
	      // impliziter Aufruf von super()
	      this.wrap = wrap;
	   }

	   public JTextPaneNowrap(StyledDocument doc)
	   {
	      super(doc);
	   }

	   public boolean getScrollableTracksViewportWidth()
	   {
	      if (wrap)
	         return super.getScrollableTracksViewportWidth() ;
	      else
	         return false ;
	   }

	   @Override 
		public void setText(String t) {
//		   maxWidth = 0;
		   super.setText(t);
		}
	   
	   public void setSize(Dimension d)
	   {
	      if(!wrap)
	      {
	    	 int prefWidth = getPreferredSize().width;
	    	 maxWidth = Math.max(maxWidth, prefWidth); 
	    	 Container parent = SwingUtilities.getUnwrappedParent(this);
	    	 System.out.println("WIDTH: "+d.width+"  PARENT: "+parent.getSize().width+"  PREFERED: "+getPreferredSize().width+"  MAX: "+maxWidth);
	         if (d.width < parent.getSize().width)
	            d.width = parent.getSize().width;
	         if (d.width < maxWidth) {
	        	 d.width = maxWidth;
	         }
	      }
	      super.setSize(d);
	   }

	   //Sets the line-wrapping policy of the JTextPaneNowrap
	   //By default this property is true, d.h. Zeilenumbruch
	   void setLineWrap(boolean wrap)
	   {
	      setVisible(false);  // alten Zustand verschwinden lassen (notwendig)
	      this.wrap = wrap ;
	      setVisible(true);   // neuen Zustand anzuzeigen (notwendig)
	   }
	}