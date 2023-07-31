package br.com.compassuol.sp.challenge.msorders;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = {"br.com.compassuol.sp.challenge.msorders.entity", "br.com.compassuol.sp.challenge.msproducts.entity"})
@EnableFeignClients
public class MsOrdersApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}


	public static void main(String[] args) {
		SpringApplication.run(MsOrdersApplication.class, args);
	}

}
