package main.bridge;

import java.util.List;

import submit.ShowPackage.OrderInfo;
import submit.ShowPackage.ShowInfo;

public interface Bridge {

	/**
	 * Adds new city
	 * 
	 * @param city city name
	 */
	void addCity(String city);

	/**
	 * Adds new hall
	 * 
	 * @param city city name
	 * @param hall hall name
	 * @param sits number of sits
	 */
	void addHall(String city, String hall, int sits);

	/**
	 * Adds new admin User
	 * 
	 * @param city city where the User is allowed to be an admin
	 * @param user User name
	 * @param pass User password
	 */
	void addAdmin(String city, String user, String pass);

	/**
	 * Adds new show
	 * 
	 * @param user     username
	 * @param pass     password
	 * @param showInfo contains show information (used to reduce the amount of
	 *                 parameters)
	 * @return If succeed returns unique show id (a positive number). Otherwise
	 *         return -1.
	 */
	int addNewShow(String user, String pass, ShowInfo showInfo);

	/**
	 * Reserves chairs for Pais members only
	 * 
	 * @param showID show id (as return from addNewShow)
	 * @param from   minimum chair id
	 * @param to     maximum chair id
	 */
	void reserveMemberChairs(int showID, int from, int to);

	/**
	 * Adds new order
	 * 
	 * @param order order information
	 * @return If succeed return an unique reservation id. Otherwise return -1.
	 */
	int newOrder(OrderInfo order);

	/**
	 * Gets waiting orders
	 * 
	 * @param id show id
	 * @return If succeed returns the list of waiting orders. Otherwise return empty
	 *         list.
	 */
	List<OrderInfo> getWaitings(int id);
}