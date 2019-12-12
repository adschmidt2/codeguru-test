package my.codeguru.example.codegurutest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class CodeguruTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeguruTestApplication.class, args);
	}

	@Bean
	public Executor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(100);
		executor.setMaxPoolSize(150);
		executor.setQueueCapacity(500);
		executor.setThreadNamePrefix("POC-");
		executor.initialize();
		return executor;
	}

}
