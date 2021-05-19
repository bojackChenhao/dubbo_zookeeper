package chen.practice.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CountDownLatch;

@SpringBootTest(classes = CommonApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CommonApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(CommonApplicationTests.class);

    private final String url = "http://127.0.0.1:8001/hello1";
    RestTemplate restTemplate = new RestTemplate();
    private  static  int MAX_THREADS = 200;
    private static CountDownLatch countDownLatch = new CountDownLatch(MAX_THREADS);
    @Test
    public void contextLoads() throws InterruptedException {
        for (int i = 0; i < MAX_THREADS; i++) {
            new Thread(() -> {
                try {
                    String result = restTemplate.getForEntity(url, String.class).getBody();
                    System.out.println(result);
                    countDownLatch.countDown();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }finally {
                    log.info("2");
                }
            }).start();
        }
        countDownLatch.await();
    }

//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//        System.out.println(responseEntity.getBody());
//
//
//    }

}
