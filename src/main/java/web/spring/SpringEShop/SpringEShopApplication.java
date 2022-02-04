package web.spring.SpringEShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import web.spring.SpringEShop.services.MailService;

import java.util.Properties;

@SpringBootApplication
public class SpringEShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEShopApplication.class, args);
	}


}
