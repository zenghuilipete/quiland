package creative.air.spring.aop;

import static org.junit.Assert.assertNotNull;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import creative.air.spring.ioc.AirClient;
import creative.air.spring.ioc.VMDevice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AnnotationAopTest {
    private final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private VMDevice arch;

    @Autowired
    @Qualifier("client")
    private AirClient ac;

    @Test
    public void shouldWireWithAutowired() {
        logger.info("\n====test start====");
        logger.debug(arch.getClass().getName());
        logger.debug(ac.getClass().getName());
        assertNotNull(arch.playing());//JdkDynamicAopProxy.invoke(Object, Method, Object[]) line: 152   
        assertNotNull(ac.playing());//CglibAopProxy$DynamicAdvisedInterceptor.intercept(Object, Method, Object[], MethodProxy) line: 602    
    }
}