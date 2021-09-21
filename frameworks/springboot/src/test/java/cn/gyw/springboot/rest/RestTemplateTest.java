package cn.gyw.springboot.rest;

import cn.gyw.springboot.DemoBootApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate 工具测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoBootApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestTemplateTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldGet() {
        String url = "http://localhost:8081/sb/foo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> requestEntity = new HttpEntity<>("test", headers);

        ResponseEntity<String> result =
                restTemplate.exchange(url + "/get", HttpMethod.GET, requestEntity, String.class);

        System.out.println(result.getBody());
    }

    @Test
    public void shouldPost() {
        String url = "http://localhost:8081/sb/foo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> requestEntity = new HttpEntity<>("test", headers);

        ResponseEntity<String> result =
                restTemplate.exchange(url + "/post", HttpMethod.POST, requestEntity, String.class);

        System.out.println(result.getBody());
    }

    @Test
    public void shouldPut() {
        String url = "http://localhost:8081/sb/foo";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        HttpEntity<String> requestEntity = new HttpEntity<>("test", headers);

        ResponseEntity<String> result =
                restTemplate.exchange(url + "/put", HttpMethod.PUT, requestEntity, String.class);

        System.out.println(result.getBody());
    }
}
