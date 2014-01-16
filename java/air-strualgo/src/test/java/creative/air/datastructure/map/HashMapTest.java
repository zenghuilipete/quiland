package creative.air.datastructure.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Test HashMap
 * 1 map：键值对 hash：hashcode 
 * 2 非线程安全 键值允许为空 键为空时的处理 
 * 3 数组+链表结构 
 * 4 负载因子loadFactor和阈值threshold 扩容机制 缓存设计 
 * 5 遍历
 * 
 * Test Cache
 * @author
 * Eric Han feuyeux@gmail.com
 * 06/09/2012
 * @since  0.0.1
 * @version 0.0.1
 */
/*
public class HashMapTest {
	static final Logger logger = Logger.getLogger(HashMapTest.class.getName());

	//@Test
	public void test2() throws Exception {
		HashMapCache<String, Integer> cacheMap = new HashMapCache<String, Integer>();
		cacheMap.clear();
		cacheMap.put("" + 1, null);
		cacheMap.put(null, 1);
		cacheMap.put(null, null);
		Assert.assertNull(cacheMap.get(null));
	}

	//@Test
	public void test5() {
		int n = 0;
		final int maxium = 5000;
		HashMapCache<String, Integer> cacheMap = new HashMapCache<String, Integer>(maxium);
		HashMapCache<String, Integer> retrievingMap = new HashMapCache<String, Integer>(maxium, maxium / 10, 0.5f);

		boolean inputRight = true;
		while (n < maxium) {
			String s = "" + n;
			try {
				cacheMap.put(s, n);
				retrievingMap.put(s, n++);
			} catch (Exception e) {
				e.printStackTrace();
				inputRight = false;
				break;
			}
		}
		Assert.assertTrue(inputRight);
		Object[] r1 = iterate(cacheMap, Level.INFO);
		Object[] r2 = iterate(retrievingMap, Level.INFO);
		logger.log(Level.INFO, "default map iterating elapse:{0}(start={1},end={2})", r1);
		logger.log(Level.INFO, "customize map iterating elapse:{0}(start={1},end={2})", r2);
		Assert.assertTrue((Long) r1[0] >= (Long) r2[0]);
	}

	private Object[] iterate(HashMapCache<String, Integer> map, Level level) {
		Iterator<Map.Entry<String, Integer>> iter = map.entrySet().iterator();
		long startTime = System.currentTimeMillis();
		while (iter.hasNext()) {
			Map.Entry<String, Integer> entry = iter.next();
			String key = entry.getKey();
			Integer val = entry.getValue();
			logger.log(level, "{0}:{1}", new Object[] { key, val });
		}
		long endTime = System.currentTimeMillis();
		return new Object[] { endTime - startTime, endTime, startTime };
	}
	
	//@Test
	public void testPutAll(){
		HashMap<String,String> map=new HashMap<String,String>();
		HashMap<String,HashMap<String,String>> map2=new HashMap<String,HashMap<String,String>>();
		
		map.put("Gateway", "Thomson");
		map2.put("patner", map);
		map.put("Gateway", "Technicolor");
		
		Iterator<Map.Entry<String,HashMap<String,String>>> iter = map2.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String,HashMap<String,String>> entry = iter.next();
			String key = entry.getKey();
			HashMap<String,String> val = entry.getValue();
			
			Iterator<Map.Entry<String,String>> iter2 = val.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String,String> entry2 = iter2.next();
				String key2 = entry2.getKey();
				String val2 = entry2.getValue();
				Assert.assertEquals("Technicolor", val2);
				logger.log(Level.INFO, "{0}:{1}", new Object[] { key2, val2 });
			}
			logger.log(Level.INFO, "{0}:{1}", new Object[] { key, val });
		}
	}
}*/
