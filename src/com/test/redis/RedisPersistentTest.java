package com.test.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisPersistentTest {
	private static Jedis jedis = new Jedis("192.168.101.112", 6379);

	public static void main(String[] args) {

		//testString();
	}

	@Test
	public void testString() {
		System.out.println("testString start");
		get("name");
		get("name1");
		set("name1", "yinaijie1");
		get("name1");
		jedis.set("name", "xinxin");// ��key-->name�з�����value-->xinxin
		System.out.println(jedis.get("name"));// ִ�н����xinxin

		jedis.append("name", " is my lover"); // ƴ��
		System.out.println(jedis.get("name"));

		jedis.del("name"); // ɾ��ĳ����
		System.out.println(jedis.get("name"));
		// ���ö����ֵ��
		jedis.mset("name", "liuling", "age", "23", "qq", "476777XXX");
		jedis.incr("age"); // ���м�1����
		System.out.println(jedis.get("name") + "-" + jedis.get("age") + "-"
				+ jedis.get("qq"));

		System.out.println("testString stop");

	}

	private static void set(String key, Object value) {
		System.out.println("[set] key:" + key + ",value:" + value);

		if (value instanceof String) {
			jedis.set(key, (String) value);
		}
	}

	private static Object get(String key) {
		Object value = jedis.get(key);
		System.out.println("[get] key:" + key + ",value:" + value);
		return value;
	}

	/**
	 * redis����Map
	 */
	@Test
	public void testMap() {
		// -----�������----------
		System.out.println("==============================");
		System.out.println("testMap start");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "xinxin");
		map.put("age", "22");
		map.put("qq", "123456");
		jedis.hmset("user", map);
		// ȡ��user�е�name��ִ�н��:[minxr]-->ע������һ�����͵�List
		// ��һ�������Ǵ���redis��map�����key����������Ƿ���map�еĶ����key�������key���Ը�������ǿɱ����
		List<String> rsmap = jedis.hmget("user", "name", "age", "qq");
		System.out.println(rsmap);

		// ɾ��map�е�ĳ����ֵ
		jedis.hdel("user", "age");
		System.out.println(jedis.hmget("user", "age")); // ��Ϊɾ���ˣ����Է��ص���null
		System.out.println(jedis.hlen("user")); // ����keyΪuser�ļ��д�ŵ�ֵ�ĸ���2
		System.out.println(jedis.exists("user"));// �Ƿ����keyΪuser�ļ�¼ ����true
		System.out.println(jedis.hkeys("user"));// ����map�����е�����key
		System.out.println(jedis.hvals("user"));// ����map�����е�����value

		Iterator<String> iter = jedis.hkeys("user").iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			System.out.println(key + ":" + jedis.hmget("user", key));
		}
		System.out.println("testMap stop");	
		System.out.println("==============================\n");
		
	}

	/**
	 * jedis����List
	 */
	@Test
	public void testList() {
		// ��ʼǰ�����Ƴ����е�����
		System.out.println("==============================");
		
		System.out.println("testList start");	
		jedis.del("java framework");
		System.out.println(jedis.lrange("java framework", 0, -1));
		// ����key java framework�д����������
		jedis.lpush("java framework", "spring");
		jedis.lpush("java framework", "struts");
		jedis.lpush("java framework", "hibernate");
		// ��ȡ����������jedis.lrange�ǰ���Χȡ����
		// ��һ����key���ڶ�������ʼλ�ã��������ǽ���λ�ã�jedis.llen��ȡ���� -1��ʾȡ������
		System.out.println(jedis.lrange("java framework", 0, -1));

		jedis.del("java framework");
		jedis.rpush("java framework", "spring");
		jedis.rpush("java framework", "struts");
		jedis.rpush("java framework", "hibernate");
		System.out.println(jedis.lrange("java framework", 0, -1));
		System.out.println("testList stop");
		System.out.println("==============================\n");
		
		
	}

	/**
	 * jedis����Set
	 */
	@Test
	public void testSet() {
		// ���
		System.out.println("==============================");
		
		System.out.println("testSet start");	
		jedis.sadd("userSet", "liuling");
		jedis.sadd("userSet", "xinxin");
		jedis.sadd("userSet", "ling");
		jedis.sadd("userSet", "zhangxinxin");
		jedis.sadd("userSet", "who");
		// �Ƴ�noname
		jedis.srem("userSet", "who");
		System.out.println(jedis.smembers("userSet"));// ��ȡ���м����value
		System.out.println(jedis.sismember("userSet", "who"));// �ж� who
															// �Ƿ���user���ϵ�Ԫ��
		System.out.println(jedis.srandmember("userSet"));
		System.out.println(jedis.scard("userSet"));// ���ؼ��ϵ�Ԫ�ظ���
		System.out.println("testSet stop");	
		System.out.println("==============================\n");
		
		
	}

	@Test
	public void test() throws InterruptedException {
		// jedis ����
		// ע�⣬�˴���rpush��lpush��List�Ĳ�������һ��˫���������ӱ��������ģ�
		System.out.println("==============================");
		
		System.out.println("test start");	
		jedis.del("a");// ��������ݣ��ټ������ݽ��в���
		jedis.rpush("a", "1");
		jedis.lpush("a", "6");
		jedis.lpush("a", "3");
		jedis.lpush("a", "9");
		System.out.println(jedis.lrange("a", 0, -1));// [9, 3, 6, 1]
		System.out.println(jedis.sort("a")); // [1, 3, 6, 9] //�����������
		System.out.println(jedis.lrange("a", 0, -1));
		System.out.println("test stop");	
		System.out.println("==============================\n");
		
	}

//	@Test
//	public void testRedisPool() {
//		RedisUtil.getJedis().set("newname", "���Ĳ���");
//		System.out.println(RedisUtil.getJedis().get("newname"));
//	}
}
