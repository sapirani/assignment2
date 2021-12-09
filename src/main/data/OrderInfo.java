package main.data;

import java.util.Arrays;

public class OrderInfo {
	public int showId;
	public String name;
	public String phone;
	public int[] chairsIds;
	public int memberId;

	@Override
	public String toString() {
		return "OrderInfo [showId=" + showId + ", name=" + name + ", phone=" + phone + ", chairsIds="
				+ Arrays.toString(chairsIds) + ", memberId=" + memberId + "]";
	}
}
