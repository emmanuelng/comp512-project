package cars.impl;

import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import cars.CarManager;

@SuppressWarnings("deprecation")
public class CarManagerImpl implements CarManager {
	
	public static void main(String[] args) {
		// Figure out where server is running
		int port = 1099;

		if (args.length == 1) {
			port = Integer.parseInt(args[0]);
		} else if (args.length != 0 && args.length != 1) {
			System.err.println("Wrong usage");
			System.exit(1);
		}

		try {
			// Create a new server object and dynamically generate the stub (client proxy)
			CarManagerImpl obj = new CarManagerImpl();
			CarManager proxyObj = (CarManager) UnicastRemoteObject.exportObject(obj, 0);

			// Bind the remote object's stub in the registry
			Registry registry = LocateRegistry.getRegistry(port);
			registry.rebind("cars.group20", proxyObj);

			System.out.println("Car server ready");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}

		// Create and install a security manager
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}
	}
}
