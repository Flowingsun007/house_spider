package com.flowingbit.data.collect.house_spider.dao;

import com.flowingbit.data.collect.house_spider.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public class RedisDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    private final static String REDIS_IP = "localhost";

    private final static int REDIS_PORT = 6379;

    public RedisDAO(){
        jedisPool = new JedisPool(REDIS_IP,REDIS_PORT);
    }

    public RedisDAO(String ip, int port){
        jedisPool = new JedisPool(ip, port);
    }


    public <T> boolean setSet(String key , Set<T> set){
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] listInfo = SerializeUtil.serialize(set);
            jedis.set(key.getBytes(), listInfo);
            return true;
        } catch (Exception e) {
            logger.error("Set key error : "+e);
        } finally {
            jedis.close();
        }
        return false;
    }

    public <T> Set<T> getSet(String key){
        Set<T> set = null;
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] in = jedis.get(key.getBytes());
            set = (Set<T>) SerializeUtil.unserialize(in);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
        } finally {
            jedis.close();
        }
        return set;
    }

    public <T> String setList(String key , List<T> list){
        String result="setList_fail";
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] listInfo = SerializeUtil.serialize(list);
            result = jedis.set(key.getBytes(), listInfo);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
        } finally {
            jedis.close();
        }
        return result;
    }

    public <T> String setList(String key , List<T> list, int seconds){
        String result="setList_fail";
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] listInfo = SerializeUtil.serialize(list);
            result = jedis.set(key.getBytes(), listInfo);
            //设置键的过期时间为seconds秒
            jedis.expire(key,seconds);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
        } finally {
            jedis.close();
        }
        return result;
    }


    public <T> List<T> getList(String key){
        List<T> list = null;
        Jedis jedis = jedisPool.getResource();
        try {
            byte[] in = jedis.get(key.getBytes());
            list = (List<T>) SerializeUtil.unserialize(in);
        } catch (Exception e) {
            logger.error("Set key error : "+e);
        } finally {
            jedis.close();
        }
        return list;
    }

    public String setString(String key , String value){
        String result=null;
        Jedis jedis = jedisPool.getResource();
        try {
            result = jedis.set(key,value);
        } catch (Exception e) {
            logger.error("RedisDAO setString error : "+e);
        } finally {
            jedis.close();
        }
        return result;
    }

    public String getString(String key){
        String result = null;
        Jedis jedis = jedisPool.getResource();
        try {
            result = jedis.get(key);
        } catch (Exception e) {
            logger.error("RedisDAO getString error : "+e);
        } finally {
            jedis.close();
        }
        return result;
    }
}

