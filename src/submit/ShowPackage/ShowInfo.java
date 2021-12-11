package submit.ShowPackage;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ShowInfo {
	public String city;
	public String hall;
	public String name;
	public String description;
	public boolean hastime;
	public LocalTime showTime;
	public long showDate;
	public long lastOrderDate;
	public double ticketCost;
	public List<OrderInfo> userstoinform = new LinkedList<>();

	public List<Integer> remained_regular_sits = new LinkedList<>();
	public List<Integer> remained_member_sits = new LinkedList<>();

	public ShowInfo()
	{
		showTime = null;
	}

	public ShowInfo(String name, String city, String hall, String desc, LocalTime show_time, long show_date, long last_date, double ticket_cost)
	{
		this.name = name;
		this.city = city;
		this.hall = hall;
		this.description = desc;
		this.hastime = (showTime == null) ? false : true;
		this.showTime = show_time;
		this.showDate = show_date;
		this.lastOrderDate = last_date;
		this.ticketCost = ticket_cost;
	}

	@Override
	public String toString() {
		return "ShowInfo [city=" + city + ", hall=" + hall + ", name=" + name + ", description=" + description
				+ ", hastime=" + hastime + ", showTime=" + showTime + ", showDate=" + convertTime(showDate)
				+ ", lastOrderDate=" + convertTime(lastOrderDate) + ", ticketCost=" + ticketCost + ", userstoinform="
				+ userstoinform + "]";
	}

	public String convertTime(long time) {
		Date date = new Date(time);
		Format format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}

	/*
	 * Check if there are enough free chairs.
	 */
	public boolean checkIfThereAreEnoughChairs(int sit_from, int sit_to)
	{
		for (int i = sit_from; i <= sit_to; i++)
		{
			if(!this.remained_regular_sits.contains(i))
				return false;
		}
		return true;
	}

	public void reserveMemberChairs(int sit_from, int sit_to)
	{
		int startIndex = this.remained_regular_sits.indexOf(sit_from);
		int endIndex = this.remained_regular_sits.indexOf(sit_to);
		List<Integer> new_list_of_chairs = new LinkedList<>();

		// add the sits to the members sits
		for(int i = startIndex; i <= endIndex; i++)
		{
			this.remained_member_sits.add(remained_regular_sits.get(i));
		}

		// update the number of regular chairs
		for(int i = 0, j = 0; i < this.remained_regular_sits.size(); i++)
		{
			if(i < startIndex || i > endIndex)
				new_list_of_chairs.add(this.remained_regular_sits.get(i));
		}
		this.remained_regular_sits = new_list_of_chairs;
	}

	/*
	 * Delete the chosen chairs from the available chairs.
	 */
	public boolean orderChairs(int[] chairs)
	{
		for (Integer chair: chairs)
		{
			if(this.remained_regular_sits.contains(chair))
				this.remained_regular_sits.remove(chair);
			else if(this.remained_member_sits.contains(chair))
				this.remained_member_sits.remove(chair);
			else
				return false;
		}
		return true;
	}

	/*
	 * Checks if the asked chairs include reserved chairs.
	 */
	public boolean checkIfContainsMemberChairs(int[] chairs)
	{
		for (Integer chair: chairs)
		{
			 if(this.remained_member_sits.contains(chair))
				return true;
		}
		return false;
	}

	/*
	 * If the show does'nt have time, add the order of the person you need to update.
	 */
	public boolean addUserToInform(OrderInfo newOrder)
	{
		boolean flag = false;
		OrderInfo orderExist = null;
		for (OrderInfo order: this.userstoinform)
		{
			// If the same user has order to the same show
			if(order.name.equals(newOrder.name) && order.showId == newOrder.showId)
			{
				flag = true;
				orderExist = order;
				break;
			}
		}

		if(!flag)
			this.userstoinform.add(newOrder);
		else
		{
			// combine the two orders
			orderExist.addChairs(newOrder.chairsIds);
			return false;
		}
		return true;
	}

	/*
	 * Initialize the available regular chairs.
	 */
	public void initializeChairs(int number_of_sits)
	{
		for(int i = 0; i < number_of_sits; i++)
			this.remained_regular_sits.add(i+1);
	}
}