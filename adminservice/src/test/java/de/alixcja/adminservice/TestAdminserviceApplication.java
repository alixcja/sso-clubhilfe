package de.alixcja.adminservice;

import org.springframework.boot.SpringApplication;

public class TestAdminserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(AdminServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
