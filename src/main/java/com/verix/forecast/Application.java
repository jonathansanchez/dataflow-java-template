package com.verix.forecast;

import com.verix.forecast.infrastructure.config.DIContainer;

public class Application {

	public static void main(String[] args) {
		System.out.println("Initializer");
		DIContainer.main(args);
	}

}
