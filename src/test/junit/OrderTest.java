package test.junit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import main.data.ShowInfo;
import main.data.OrderInfo;

public class OrderTest extends ProjectTest {

	private int futureShowId;
	private int pastShowId;
	private OrderInfo order0;
	private OrderInfo order1;
	private static final int ERROR = -1;

	@Before
	public void setUp() {
		super.setUp();

		ShowInfo show0 = createShow0();
		this.futureShowId = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], show0);
		this.reserveMemberChairs(futureShowId, 30, 60);

		ShowInfo show2 = createShow2();
		this.pastShowId = this.addNewShow(this.users[0][USER_USER], this.users[0][USER_PASS], show2);
		this.reserveMemberChairs(pastShowId, 1, 30);

		order0 = createOrder0(futureShowId);
		order1 = createOrder1(futureShowId);
	}

	@Test
	public void testPlaceOrder() {
		int reservationId0, reservationId1;

		reservationId0 = this.newOrder(order0);
		assertTrue(reservationId0 > 0);
		assertTrue(this.getWaitings(order0.showId).contains(order0));

		reservationId1 = this.newOrder(order1);
		assertTrue(reservationId1 > 0);
		assertTrue(this.getWaitings(order1.showId).contains(order0));
		assertTrue(this.getWaitings(order1.showId).contains(order1));
		assertTrue("Different orders should have different ids!", reservationId0 != reservationId1);
	}

	@Test
	public void testPlaceOrderNotMember() {
		int reservationId;

		order0.memberId = -5;
		reservationId = this.newOrder(order0);
		assertEquals("Only Pais members can order reserved chairs!", reservationId, ERROR);

		order1.memberId = 0;
		reservationId = this.newOrder(order1);
		assertEquals("Only Pais members can order reserved chairs!", reservationId, ERROR);
	}

	@Test
	public void testPlacePastOrder() {
		int reservationId0, reservationId1;

		order0.showId = this.pastShowId;
		order1.showId = this.pastShowId;

		reservationId0 = this.newOrder(order0);
		assertEquals("member can not order tickets after last order date!", reservationId0, ERROR);

		reservationId1 = this.newOrder(order1);
		assertEquals("member can not order tickets after last order date!", reservationId1, ERROR);
	}

	@Test
	public void testPlaceUnknownOrder() {
		int reservationId0, reservationId1;

		order0.showId = -243;
		do {
			order1.showId = (int) (Math.random() * 10000) + 100;
		} while (order1.showId == this.futureShowId || order1.showId == this.pastShowId);

		reservationId0 = this.newOrder(order0);
		assertEquals("member can not order tickets to unknown show!", reservationId0, ERROR);

		reservationId1 = this.newOrder(order1);
		assertEquals("member can not order tickets to unknown show!", reservationId1, ERROR);
	}

	@Test
	public void testPlaceOrderMissingInfo1() {
		int reservationId0, reservationId1;

		order0.name = null;
		order1.phone = "";

		reservationId0 = this.newOrder(order0);
		assertEquals("member can not order tickets without name!", reservationId0, ERROR);

		reservationId1 = this.newOrder(order1);
		assertEquals("member can not order tickets without phone number!", reservationId1, ERROR);
	}

	@Test
	public void testPlaceOrderMissingInfo2() {
		int reservationId0, reservationId1;

		order0.chairsIds = null;
		order1.chairsIds = new int[0];

		reservationId0 = this.newOrder(order0);
		assertEquals("member can not order tickets without chairs!", reservationId0, ERROR);

		reservationId1 = this.newOrder(order1);
		assertEquals("member can not order tickets without chairs!", reservationId1, ERROR);
	}

	@Test
	public void testSameUser() {
		int reservationId0, reservationId1;

		order1.name = order0.name;

		reservationId0 = this.newOrder(order0);
		assertTrue(reservationId0 > 0);
		assertTrue(this.getWaitings(order0.showId).contains(order0));

		reservationId1 = this.newOrder(order1);
		assertTrue(reservationId1 > 0);
		assertTrue(this.getWaitings(order1.showId).contains(order0));
		assertFalse(this.getWaitings(order1.showId).contains(order1));
	}
}
