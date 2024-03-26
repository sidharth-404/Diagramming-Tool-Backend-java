package com.diagrammingtool.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DigrammingToolAppApplicationTests {

	@Test
	void contextLoads() {
		assertDoesNotThrow(() -> DigrammingToolAppApplication.main(new String[] {}));
	}

}
