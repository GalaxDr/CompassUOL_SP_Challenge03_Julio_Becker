package br.com.compassuol.sp.challenge.msproducts;

import br.com.compassuol.sp.challenge.msproducts.rabbitmq.MyResponseHandler;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsProductsApplication {

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(MsProductsApplication.class, args);
	}

}
