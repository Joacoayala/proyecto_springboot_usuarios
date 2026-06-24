package com.devops.springboot_app_devops;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootAppDevopsApplicationTests {

	@Test
	void contextLoads() {
        org.junit.jupiter.api.Assertions.assertEquals(1, 2, "Fallo intencional para demostrar bloqueo de pipeline");
	}

}
