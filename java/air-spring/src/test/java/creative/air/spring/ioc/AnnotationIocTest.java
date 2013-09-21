package creative.air.spring.ioc;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AnnotationIocTest {
    @Autowired
    private VMDevice arch;

    @Autowired
    @Qualifier("ci")
    private VMDevice device2;

    @Autowired
    @Qualifier("client")
    private AirClient device0;

    @Test
    public void shouldWireWithAutowired() {
        assertNotNull(arch.playing());
        assertNotNull(device2.playing());
        assertNull(device0.getServerHost());
    }
}