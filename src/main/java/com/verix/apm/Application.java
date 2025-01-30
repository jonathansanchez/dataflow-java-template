package com.verix.apm;

import com.verix.apm.infrastructure.config.DIContainer;

public class Application {

	public static void main(String[] args) {
		//new PipelineApm(args);
		//new Prueba(args);
		System.out.println("Initializer");
		DIContainer.main(args);
	}

}
