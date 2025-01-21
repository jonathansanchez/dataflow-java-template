package com.verix.sam;

import com.verix.sam.infrastructure.config.DIContainer;

public class Application {

	public static void main(String[] args) {
		System.out.println("Initializer");
		DIContainer.main(args);
	}

}
