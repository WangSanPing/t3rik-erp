package com.t3rik.common.core.redis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * RedissonUtil
 *
 * @author t3rik
 * @date 2024/12/29 23:21
 */
@Slf4j
@Component
public class RedissonUtil {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 获取分布式锁
     *
     * @param lockKey 锁的名称
     * @return 返回锁对象
     */
    public RLock getLock(String lockKey) {
        return redissonClient.getLock(lockKey);
    }

    /**
     * 获取带过期时间的分布式锁
     *
     * @param lockKey   锁的名称
     * @param leaseTime 锁的过期时间（秒）
     * @return 返回锁对象
     */
    public RLock getLock(String lockKey, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 获取 RMap 对象
     *
     * @param mapName Redis 中的哈希表名称
     * @return RMap 对象
     */
    public <K, V> RMap<K, V> getMap(String mapName) {
        return redissonClient.getMap(mapName);
    }

    /**
     * 获取缓存并设置过期时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间，单位：秒
     */
    public void setCache(String key, String value, long timeout) {
        redissonClient.getBucket(key).set(value, Duration.ofSeconds(timeout));
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 返回缓存值
     */
    public <T> T getCache(String key) {
        return (T) redissonClient.getBucket(key).get();
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void deleteCache(String key) {
        redissonClient.getBucket(key).delete();
    }

    /**
     * 尝试获取锁，如果获取不到则等待指定时间
     *
     * @param lockKey   锁的名称
     * @param waitTime  等待时间（秒）
     * @param leaseTime 锁的持有时间（秒）
     * @return 是否获取到锁
     */
    public boolean tryLock(String lockKey, long waitTime, long leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("获取锁失败， 异常信息 {}", e.getMessage());
        }
        return false;
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的key
     */
    public void releaseLock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        this.releaseLock(lock);
    }

    /**
     * 释放锁
     *
     * @param lock 锁对象
     */
    public void releaseLock(RLock lock) {
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }

    /**
     * 设置信号量
     *
     * @param semaphoreKey key
     * @param permits      信号量数量
     */
    public void setSemaphore(String semaphoreKey, int permits) {
        // 获取信号量对象
        RSemaphore semaphore = redissonClient.getSemaphore(semaphoreKey);

        // 初始化信号量，设置许可数量
        semaphore.trySetPermits(permits);  // 设置许可数量
    }

    /**
     * 获取信号量
     *
     * @param semaphoreKey key
     * @param permits      信号量数量
     */
    public boolean acquireSemaphore(String semaphoreKey, int permits) {
        var semaphore = redissonClient.getSemaphore(semaphoreKey);
        try {
            return semaphore.tryAcquire(permits, Duration.ofMillis(1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * 释放信号量
     *
     * @param semaphoreKey key
     */
    public void releaseSemaphore(String semaphoreKey) {
        var semaphore = redissonClient.getSemaphore(semaphoreKey);
        semaphore.release();
    }

}
