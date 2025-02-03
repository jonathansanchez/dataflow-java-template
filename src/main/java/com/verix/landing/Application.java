package com.verix.landing;

import com.verix.landing.infrastructure.config.DIContainer;

public class Application {

	public static void main(String[] args) {
		//new DeletePipeline(args);

		System.out.println("Initializer");
		DIContainer.main(args);
	}

}
