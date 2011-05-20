public class Mp3Player 
{

	private Model theM;
	private View theV;
	private Controller theC;
	
	public Mp3Player()
	{
		theM = new Model();
		theC = new Controller(theM);
		theV = new View(theM, theC);
	}
	
	public static void main(String[] args) 
	{
		Mp3Player thePlayer = new Mp3Player();
	}

}
