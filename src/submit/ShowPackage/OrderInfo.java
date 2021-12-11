package submit.ShowPackage;

import java.util.Arrays;

public class OrderInfo {
	public int showId;
	public String name;
	public String phone;
	public int[] chairsIds;
	public int memberId;

	public OrderInfo()
	{

	}

	public OrderInfo(int showId, String name, String phone, int[] chairsIds, int memberId)
	{
		this.showId = showId;
		this.name = name;
		this.phone = phone;
		this.chairsIds = chairsIds;
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "OrderInfo [showId=" + showId + ", name=" + name + ", phone=" + phone + ", chairsIds="
				+ Arrays.toString(chairsIds) + ", memberId=" + memberId + "]";
	}

	public void addChairs(int[] chairsIds)
	{
		int[] new_chairs = new int[this.chairsIds.length + chairsIds.length];
		System.arraycopy(this.chairsIds, 0, new_chairs, 0, this.chairsIds.length);
		System.arraycopy(chairsIds, 0, new_chairs, this.chairsIds.length, chairsIds.length);

		this.chairsIds = new_chairs;
	}
}
