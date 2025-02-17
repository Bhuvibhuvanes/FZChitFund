package fz.chitfund;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "fz.chitfund.repository")
public class FrenzoChitFundApplication {

	public static void main(String[] args) {
		SpringApplication.run(FrenzoChitFundApplication.class, args);
	}

}
