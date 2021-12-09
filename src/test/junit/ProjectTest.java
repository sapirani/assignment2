package test.junit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import test.bridge.Driver;
import main.bridge.Bridge;
import main.data.OrderInfo;
import main.data.ShowInfo;

public abstract class ProjectTest {
	private Bridge bridge;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private static final int thisyear;

	static {
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		thisyear = calendar.get(Calendar.YEAR);
	}

	// SetUp information
	
	protected final String[] cities = { "Beer-Sheva", "Tel-Aviv", "Haifa" };
	protected final Object[][] halls = { { cities[0], "hall 1", 300 }, { cities[1], "hall 1", 230 },
			{ cities[1], "hall 2", 413 }, };
	public static final int HALL_CITY = 0, HALL_NAME = 1, HALL_SITS = 2;
	protected final String[][] users = { { cities[0], "bAdmin", "bPassWord" }, { cities[1], "tlv", "abcd1234" },
			{ cities[2], "green", "house" } };
	public static final int USER_CITY = 0, USER_USER = 1, USER_PASS = 2;

	public void setUp() {
		this.bridge = Driver.getBridge();
		setUpCities();
		setUpHalls();
		setUpUsers();
	}

	private void setUpCities() {
		for (String city : cities)
			this.bridge.addCity(city);
	}

	private void setUpHalls() {
		for (Object[] hallInfo : halls) {
			this.bridge.addHall((String) hallInfo[HALL_CITY], (String) hallInfo[HALL_NAME],
					(Integer) hallInfo[HALL_SITS]);
		}
	}

	private void setUpUsers() {
		for (String[] userInfo : users) {
			this.bridge.addAdmin(userInfo[USER_CITY], userInfo[USER_USER], userInfo[USER_PASS]);
		}
	}

	// Tested procedures
	
	public int addNewShow(String user, String pass, ShowInfo showInfo) {
		return this.bridge.addNewShow(user, pass, showInfo);
	}

	public void reserveMemberChairs(int showID, int from, int to) {
		this.bridge.reserveMemberChairs(showID, from, to);
	}

	public int newOrder(OrderInfo order) {
		return this.bridge.newOrder(order);
	}

	public List<OrderInfo> getWaitings(int id) {
		return this.bridge.getWaitings(id);
	}

	// Auxiliary procedures
	
	protected ShowInfo createShow0() {
		// Good show
		ShowInfo newShow = new ShowInfo();
		int futureyear = thisyear + 1;
		
		newShow.city = (String) this.halls[0][HALL_CITY];
		newShow.hall = (String) this.halls[0][HALL_NAME];
		newShow.description = "A great show";
		newShow.name = "A great show name";
		newShow.ticketCost = 120;
		newShow.hastime = false;
		try {
			newShow.lastOrderDate = dateFormat.parse("20.2." + futureyear).getTime();
			newShow.showDate = dateFormat.parse("20.3." + futureyear).getTime();
		} catch (ParseException e) {
			System.err.println("Wrong time or date: " + e.getMessage());
		}
		return newShow;
	}

	protected ShowInfo createShow1() {
		// Another good show
		ShowInfo newShow = new ShowInfo();
		int futureyear = thisyear + 1;
		
		newShow.city = (String) this.halls[1][HALL_CITY];
		newShow.hall = (String) this.halls[1][HALL_NAME];
		newShow.description = "Another great show";
		newShow.name = "Another great show name";
		newShow.ticketCost = 30;
		newShow.hastime = false;
		try {
			newShow.lastOrderDate = dateFormat.parse("25.2." + futureyear).getTime();
			newShow.showDate = dateFormat.parse("30.03." + futureyear).getTime();
		} catch (ParseException e) {
			System.err.println("Wrong time or date: " + e.getMessage());
		}
		return newShow;
	}

	protected ShowInfo createShow2() {
		// The first show, past version
		ShowInfo newShow = createShow0();
		int pastyear = thisyear - 1;
		
		try {
			newShow.lastOrderDate = dateFormat.parse("20.02." + pastyear).getTime();
		} catch (ParseException e) {
			System.err.println("Wrong time or date: " + e.getMessage());
		}
		return newShow;
	}

	protected OrderInfo createOrder0(int showId) {
		OrderInfo newOrder = new OrderInfo();
		
		newOrder.name = "Ariela";
		newOrder.phone = "03-6940177";
		newOrder.chairsIds = new int[] { 50, 51, 52, 53 };
		newOrder.memberId = 345;
		newOrder.showId = showId;
		return newOrder;
	}

	protected OrderInfo createOrder1(int showId) {
		OrderInfo newOrder = new OrderInfo();
		
		newOrder.name = "Ariel";
		newOrder.phone = "03-6940320";
		newOrder.chairsIds = new int[] { 35, 36 };
		newOrder.memberId = 11122322;
		newOrder.showId = showId;
		return newOrder;
	}
}
