package chen.practice.provider.service;

import org.apache.dubbo.config.annotation.DubboService;
import chen.practice.common.service.IHelloService;
@DubboService
public class HelloService implements IHelloService{
    public String hello() {
        // TODO Auto-generated method stub
        return "Hello World!";
    }
}
