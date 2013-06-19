package creative.air.spring.ioc;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import creative.air.spring.VMDevice;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class AnnotationIocTest {
	@Autowired
	@Qualifier("arch")
<<<<<<< HEAD
	private ArchServer archServer;

	@Test
	public void testPlaying() {
		assertNotNull(archServer.playing());
=======
	private VMDevice	device1;

	@Autowired
	@Qualifier("ci")
	private VMDevice	device2;

	@Test
	public void shouldWireWithAutowire() {
		assertNotNull(device1.playing());
		assertNotNull(device2.playing());
>>>>>>> ioo
	}
}