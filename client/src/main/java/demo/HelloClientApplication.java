package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.feign.FeignClientScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@FeignClientScan
public class HelloClientApplication {
	@Autowired
	HelloClient client;

	@Autowired
	HelloClient2 client2;

	@RequestMapping("/dothis")
	public String doFirst() {
		return client.hello();
	}
	@RequestMapping("/dothat")
	public String doSecond() {
		return client2.hello2();
	}
	@RequestMapping("/doboth")
	public String doBoth() {
		return client.hello() +" " + client2.hello2();
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloClientApplication.class, args);
	}

	@FeignClient("HelloServer")
	interface HelloClient {
		@RequestMapping(value = "/microsvc1/dosomething", method = GET)
		String hello();
	}
	@FeignClient("HelloServer2")
	interface HelloClient2 {
		
		@RequestMapping(value = "/microsvc2/dosomethingelse", method = GET)
		String hello2();
	}
}
