public class Room
{
	private int roomNum;
	private boolean isReserved;
	private Account reservedBy;
	
	public Room(int roomNum)
	{
		this.roomNum = roomNum;
		isReserved = false;
		reservedBy = null;
	}
}
