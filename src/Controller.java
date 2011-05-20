import java.awt.event.*;
import java.io.File;
import javax.swing.JFileChooser;


public class Controller implements ActionListener
{

	private Model theM;
	private JFileChooser chooseFile;
	private File file;
	
	public Controller(Model _theM) 
	{
		this.theM = _theM;
		chooseFile = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand() == "open")
		{
			chooseFile.showOpenDialog(null);
			file = chooseFile.getSelectedFile();
			if(!theM.isPlaying())
			{
				String fileName = file.getName();
				if(fileName.contains(".mp3"))
				{
					theM.load(file.getPath());
				}
			}
		}
		else if(e.getActionCommand() == "play" && theM.isLoaded() && !theM.isPlaying())
		{
			theM.play();
		}
		else if(e.getActionCommand() == "stop" && theM.isPlaying())
		{
			theM.stop();
		}
		else
		{
			System.out.println("Error in the controller");
		}
		
	}
	
	public String getSongtitle()
	{
		String songTitle = file.getName();
		return songTitle;
	}

}
