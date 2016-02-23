import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class GUI extends Frame 
{
	TextArea ground=new TextArea();
	TextField msg=new TextField(60);
	Frame f=new Frame("±ú²²¿À");
	GUI()
	{
		f.setSize(500, 600);
		f.setLayout(new GridLayout(2, 1,20,20));
		ground.setSize(500, 600);
		ground.setEditable(false);
		ground.setBackground(Color.cyan);
		
		msg.setSize(200, 50);
		msg.setEditable(true);
		f.add(ground);
		f.add(msg);
		f.addWindowListener(new WindowListener() 
		{
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		f.setVisible(true);
		
	}
	
	public static void main(String[] args)
	{
		new GUI();
	}
}