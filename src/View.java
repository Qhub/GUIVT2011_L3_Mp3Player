import java.awt.*;
import java.util.*;

import javax.swing.*;


public class View extends JPanel implements Observer
{

	private JFrame theFrame;
	private JPanel buttonsPanel, songPanel;
	private JButton play, stop, open;
	private JLabel runtime;
	private Controller theC;
	private Model theM;
	
	public View(Model _theM, Controller _theC) 
	{
		super(new BorderLayout());
		
		theC = _theC;
		theM = _theM;
		
		//Frame
		theFrame = new JFrame("The awesomest player of them all!");
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theFrame.setSize(250,95);
		theFrame.setResizable(false);
		theFrame.setVisible(true);
		
		//Panels
		buttonsPanel = new JPanel();
		buttonsPanel.setPreferredSize(new Dimension(275, 45));
		buttonsPanel.setBackground(Color.BLACK);
		theFrame.add(BorderLayout.NORTH, buttonsPanel);
		songPanel = new JPanel();
		songPanel.setBackground(Color.BLACK);
		theFrame.add(BorderLayout.SOUTH, songPanel);
		
		//Buttons
		play = new JButton("PLAY");
		stop = new JButton("STOP");
		open = new JButton("OPEN");
		play.setActionCommand("play");
		stop.setActionCommand("stop");
		open.setActionCommand("open");
		play.addActionListener(theC);
		stop.addActionListener(theC);
		open.addActionListener(theC);
		buttonsPanel.add(play);
		buttonsPanel.add(stop);
		buttonsPanel.add(open);
		
		//Label to show runtime
		runtime = new JLabel();
		runtime.setForeground(Color.WHITE);
		runtime.setPreferredSize(new Dimension(120, 30));
		songPanel.add(runtime);
		
		theM.addObserver(this);
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		runtime.setText("Run time: " + theM.getPosition());
		theFrame.setTitle(theC.getSongtitle());
	}

}
