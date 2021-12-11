package main.data;

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

	public ShowInfo(String name, String city, String hall, String desc, LocalTime show_time, long show_date, long last_date, double ticket_cost, int available_sits)
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

		for(int i = 0; i < available_sits; i++)
			this.remained_regular_sits.add(i+1);
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
}