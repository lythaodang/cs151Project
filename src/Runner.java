/**
 * COPYRIGHT 2014 InfiniteLoops. All Rights Reserved. 
 * Hotel Management 
 * CS151 Group Project
 * @author Mike Phe, Ly Dang, Andrew Yobs
 * @version 1.00 2014/10/30
 */

public class Runner {

	public static void main(String[] args) {
		Model database = new Model();
		@SuppressWarnings("unused")
		ViewManager viewer = new ViewManager(database);
	}
}
