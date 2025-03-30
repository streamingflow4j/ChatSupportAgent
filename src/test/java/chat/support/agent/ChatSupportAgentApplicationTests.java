package chat.support.agent;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ChatSupportAgentApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		assertThat(applicationContext).isNotNull();
	}

	@Test
	void testMessageServiceBeanExists() {
		boolean beanExists = applicationContext.containsBean("messageService");
		assertThat(beanExists).isTrue();
	}

	@Test
	void testFileStoreServiceBeanExists() {
		boolean beanExists = applicationContext.containsBean("fileStoreService");
		assertThat(beanExists).isTrue();
	}
}