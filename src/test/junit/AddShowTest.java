package test.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import main.data.ShowInfo;

public class AddShowTest extends ProjectTest {

	private ShowInfo goodShow0;
	private ShowInfo goodShow1;
	private static final int ERROR = -1;

	@Before
	public void setUp() {
		super.setUp();
		this.goodShow0 = createShow0();
		this.goodShow1 = createShow1();
	}

	@After
	public void tearDown() {
		this.goodShow0 = null;
		this.goodShow1 = null;
	}

	@Test
	public void testAddShow() {
		int showId0, showId1;

		showId0 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], this.goodShow0);
		assertTrue(showId0 > 0);

		showId1 = this.addNewShow(this.users[1][USER_USER], this.users[1][USER_PASS], this.goodShow1);
		assertTrue(showId1 > 0);

		assertTrue("Different shows must have different ids !", showId0 != showId1);
	}

	@Test
	public void testAddShowWrongUserLoc() {
		int showId0, showId1;

		showId0 = this.addNewShow(this.users[1][USER_USER], this.users[1][USER_PASS], this.goodShow0);
		assertEquals(this.users[1][USER_CITY] + " admin add show to " + this.goodShow0.city, ERROR, showId0);

		showId1 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], this.goodShow1);
		assertEquals(this.users[0][USER_CITY] + " admin add show to " + this.goodShow1.city, ERROR, showId1);
	}

	@Test
	public void testAddShowWrongUserOrPass() {
		int showId0, showId1, showId2, showId3, showId4, showId5, showId6;

		showId0 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS] + "xxx", this.goodShow0);
		assertEquals(ERROR, showId0);

		showId1 = this.addNewShow(this.users[1][USER_USER], this.users[0][USER_PASS], this.goodShow1);
		assertEquals(ERROR, showId1);

		showId2 = this.addNewShow("noExists", this.users[1][USER_PASS], this.goodShow1);
		assertEquals(ERROR, showId2);

		showId3 = this.addNewShow(null, this.users[0][USER_PASS], this.goodShow0);
		assertEquals(ERROR, showId3);

		showId4 = this.addNewShow("", this.users[0][USER_PASS], this.goodShow0);
		assertEquals(ERROR, showId4);

		showId5 = this.addNewShow(this.users[1][USER_USER], null, this.goodShow1);
		assertEquals(ERROR, showId5);

		showId6 = this.addNewShow(this.users[1][USER_USER], "", this.goodShow1);
		assertEquals(ERROR, showId6);
	}

	@Test
	public void testAddShowWrongDates() {
		int showId0, showId1;

		switchShowDates(goodShow0);
		switchShowDates(goodShow1);

		showId0 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], this.goodShow0);
		assertEquals(ERROR, showId0);

		showId1 = this.addNewShow(this.users[1][USER_USER], this.users[1][USER_PASS], this.goodShow1);
		assertEquals(ERROR, showId1);
	}

	@Test
	public void testAddShowMissingLoc() {
		int showId0, showId1;

		goodShow0.city = null;
		goodShow1.hall = null;

		showId0 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], this.goodShow0);
		assertEquals(ERROR, showId0);

		showId1 = this.addNewShow(this.users[1][USER_USER], this.users[1][USER_PASS], this.goodShow1);
		assertEquals(ERROR, showId1);
	}

	@Test
	public void testAddShowWithWrongMissingDate() {
		int showId0, showId1;

		this.goodShow0.hastime = true;
		this.goodShow1.showTime = LocalTime.parse("20:00");

		showId0 = this.addNewShow(this.users[1][USER_USER], this.users[1][USER_PASS], this.goodShow0);
		assertEquals(ERROR, showId0);

		showId1 = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], this.goodShow1);
		assertEquals(ERROR, showId1);
	}

	// Auxiliary procedure
	private void switchShowDates(ShowInfo show) {
		long tmp = show.lastOrderDate;
		show.lastOrderDate = show.showDate;
		show.showDate = tmp;
	}
}