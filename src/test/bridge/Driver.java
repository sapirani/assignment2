package test.bridge;

import main.bridge.Bridge;

public abstract class Driver {

	public static Bridge getBridge() {
		ProxyBridge bridge = new ProxyBridge();

		// Uncomment this line
		 bridge.setRealBridge(new submit.RealBridge());

		return bridge;
	}
}
