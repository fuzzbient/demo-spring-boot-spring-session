package demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableRedisHttpSession
public class SpringsessionApplication {
	
	@RequestMapping("/")
    public Map<String,String> home(HttpServletRequest request, ModelMap model) {
		for (int i = 0; i < 10; i++) {
			String val = (String) request.getSession().getAttribute("val[" + i + "]");
			if (val == null) {
				System.out.println(">>>>>>>> Setting val[" + i + "]...");
				request.getSession().setAttribute("val[" + i + "]", "val[" + i + "] Created @ " + new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa").format(new Date()));
			}
		}
		
		Map<String,String> result = new HashMap<String,String>();
		Enumeration<String> attrs =  request.getSession().getAttributeNames();
		while(attrs.hasMoreElements()) {
			String name = attrs.nextElement();
			result.put(name, request.getSession().getAttribute(name).toString());
		}
        return result;
    }	

    public static void main(String[] args) {
        SpringApplication.run(SpringsessionApplication.class, args);
    }
}
