package chen.practice.consumer.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import chen.practice.common.service.IHelloService;


@RestController
public class HelloController {
    @DubboReference
    private IHelloService helloService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return helloService.hello();
    }
}
