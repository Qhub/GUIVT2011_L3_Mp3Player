import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;

import javazoom.jl.player.Player;

/**
 *
 * @author Jonas
 */
public class Model extends Observable {

	private Player player = null;
	private Thread pt = null;
	private Thread ut = null;
	private String mp3file = null;
	//For formatting the output 
	private DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");
	private Calendar calendar = Calendar.getInstance();
	
	//State variables
	private boolean playing = false;
	private boolean closed = true;
	private boolean dialog = false;

	// Private class
	// start the player and runs it until it is completed if not stopped by user
	private class playerThread extends Thread /*implements Runnable*/
	{
		public void run()
		{
			try	{
				player.play();
				player.close();
				playing = false;
				closed = true;
				setChanged();
				notifyObservers();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// Private class
	// regulary update the observers about the status 
	private class updateThread extends Thread /*implements Runnable*/
	{
		public void run()
		{
			try	{
				while(isPlaying()) {
					setChanged();
					notifyObservers();
					Thread.sleep(100);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// pre : No file is playing (= !isPlaying()) and input is an path to an mp3 file
	// post: Mp3 file defined by input is loaded and object is initialized and ready to play
	public void load( String filename )
	{
		mp3file = filename;
		try {
			player = new Player( new FileInputStream( mp3file ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		pt = new playerThread(); 
		ut = new updateThread(); 
		closed = false;
		dialog = false;
		setChanged();
		notifyObservers();
	}

	//pre : Mp3 file loaded and object initialized
	//post:	Mp3 file is played on the standard output device
	public void play()
	{
		if (closed) {
			load(mp3file);
		}
		playing = true;
		closed = false;
		pt.start();
		ut.start();
		setChanged();
		notifyObservers();
	}

	//pre : Mp3 file is not stopped (= isPlaying)
	//post:	Mp3 file is stopped
	public void stop()
	{
		player.close();
	}

	//pre : true
	//post:	return true if Mp3 file is playing otherwise false
	public boolean isPlaying() {
		return playing;
	}

	//pre : true
	//post:	return true if Mp3 file is loaded otherwise false
	public boolean isLoaded() {
		return (mp3file != null);
	}

	public void showDialog() {
		dialog = true;
		setChanged();
		notifyObservers();
	}

	//pre : Mp3 file loaded and object initialized
	//post:	return current position in milliseconds
	public String getPosition()
	{
		calendar.setTimeInMillis(player.getPosition());
		return formatter.format(calendar.getTime());
	}

	public boolean getStat(String string) {
		if (string == "play") 
			if (!isPlaying() && isLoaded()) 
				return true;
		
		if (string == "load")
			if (!isPlaying())
				return true;
		
		if (string == "stop")
			if (isPlaying()) 
				return true;
		
		if (string == "showDialog")
			if (!isPlaying() && dialog) 
				return true;
		
		if (string == "showPos")
			if (isPlaying()) 
				return true;
			
		return false;
	}
}