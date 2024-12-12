package comparus.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/test.properties")
public class SlideshowControllerIT {

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testAddImage() {
		testRestTemplate.getForObject("/addImage", String.class);
	}
}
