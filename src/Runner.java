
public class Runner
{

	public static void main(String[] args)
	{
		DatabaseModel database = new DatabaseModel();
		ViewManager viewer = new ViewManager(database);
	}
}
