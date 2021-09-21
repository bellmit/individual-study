package cn.gyw.frameworks.h2;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class H2Controller {
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private LocationRepository locationRepository;

	@ResponseBody
	@RequestMapping("/hello")
	public List<Location> hello() {
		System.out.println(dataSource);
		
		return locationRepository.findAll();
	}

}
