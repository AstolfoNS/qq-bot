package com.astolfo.robotservice.infrastructure.common.utils;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisCacheUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    public Boolean expire(
            String key,
            long timeout,
            TimeUnit unit
    ) {
        this.validateKey(key);

        return redisTemplate.expire(key, timeout, unit);
    }

    public Boolean hasKey(String key) {
        this.validateKey(key);

        return redisTemplate.hasKey(key);
    }

    public Boolean delete(String key) {
        this.validateKey(key);

        return redisTemplate.delete(key);
    }

    public Long delete(Collection<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return 0L;
        }
        return redisTemplate.delete(keys);
    }

    public Set<String> keys(String pattern) {
        if (!StringUtils.hasText(pattern)) {
            return Collections.emptySet();
        }
        return redisTemplate.keys(pattern);
    }

    public <T> void set(String key, T value) {
        this.validateKey(key);

        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void set(
            String key,
            T value,
            long timeout,
            TimeUnit unit
    ) {
        this.validateKey(key);

        if (timeout <= 0) {
            throw new IllegalArgumentException("过期时间必须大于0");
        }
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        this.validateKey(key);

        return (T) redisTemplate.opsForValue().get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, T defaultValue) {
        return Optional.ofNullable((T) get(key)).orElse(defaultValue);
    }

    public <T> Long setList(String key, @NotNull List<T> list) {
        this.validateKey(key);

        if (list.isEmpty()) {
            return 0L;
        }
        return redisTemplate.opsForList().rightPushAll(key, list.toArray());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getList(String key) {
        this.validateKey(key);

        return Optional.ofNullable((List<T>) redisTemplate.opsForList().range(key, 0, -1)).orElse(Collections.emptyList());
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getListOrNull(String key) {
        this.validateKey(key);

        return hasKey((key)) ? (List<T>) redisTemplate.opsForList().range(key, 0, -1) : null;
    }

    public <T> Long setSet(String key, @NotNull Set<T> set) {
        this.validateKey(key);

        if (set.isEmpty()) {
            return 0L;
        }
        return redisTemplate.opsForSet().add(key, set.toArray());
    }

    @SuppressWarnings("unchecked")
    public <T> Set<T> getSet(String key) {
        this.validateKey(key);

        return Optional.ofNullable((Set<T>) redisTemplate.opsForSet().members(key)).orElse(Collections.emptySet());
    }

    public <T> void putHash(
            String key,
            String hashKey,
            T value
    ) {
        this.validateKey(key);

        this.validateHashKey(hashKey);

        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T getHashValue(String key, String hashKey) {
        this.validateKey(key);

        this.validateHashKey(hashKey);

        return (T) redisTemplate.opsForHash().get(key, hashKey);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getHashMultiValues(String key, Collection<Object> hashKeys) {
        this.validateKey(key);

        if (CollectionUtils.isEmpty(hashKeys)) {
            return Collections.emptyList();
        }
        return (List<T>) redisTemplate.opsForHash().multiGet(key, hashKeys);
    }

    public Long removeHashValue(String key, String hashKey) {
        this.validateKey(key);

        this.validateHashKey(hashKey);

        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    public <T> Map<String, T> getHashAll(String key, Class<T> clazz) {
        this.validateKey(key);

        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);

        if (entries.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, T> result = new HashMap<>(entries.size());

        entries.forEach((k, v) -> {
            if (k instanceof String && clazz.isInstance(v)) {
                result.put((String) k, clazz.cast(v));
            }
        });

        return result;
    }

    // ==================== 分布式锁操作 ====================

    /**
     * 尝试获取锁
     *
     * @param lockKey   锁的key
     * @param waitTime  等待时间
     * @param leaseTime 锁持有时间
     * @param unit      时间单位
     * @return 锁对象，获取失败返回null
     */
    public RLock tryLock(
            String lockKey,
            long waitTime,
            long leaseTime,
            TimeUnit unit
    ) {
        this.validateKey(lockKey);

        if (waitTime < 0 || leaseTime <= 0) {
            throw new IllegalArgumentException("等待时间不能为负数，锁持有时间必须大于0");
        }

        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit) ? lock : null;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            log.warn("尝试获取锁被中断：{}", lockKey, exception);

            return null;
        } catch (Exception exception) {
            log.error("尝试获取锁异常：{}", lockKey, exception);

            return null;
        }
    }

    /**
     * 释放锁
     *
     * @param lock 锁对象
     */
    public void unlock(RLock lock) {
        if (lock == null) {
            return;
        }

        try {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();

                log.debug("成功释放锁");
            } else {
                log.warn("当前线程未持有锁，无法释放");
            }
        } catch (Exception e) {
            log.error("释放锁失败", e);
        }
    }

    /**
     * 执行带锁的操作
     *
     * @param lockKey   锁的key
     * @param waitTime  等待时间
     * @param leaseTime 锁持有时间
     * @param unit      时间单位
     * @param task      要执行的任务
     * @return 是否执行成功
     */
    public boolean executeWithLock(
            String lockKey,
            long waitTime,
            long leaseTime,
            TimeUnit unit,
            Runnable task
    ) {
        RLock lock = tryLock(lockKey, waitTime, leaseTime, unit);

        if (lock == null) {
            return false;
        }

        try {
            task.run();
            return true;
        } finally {
            unlock(lock);
        }
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 验证key是否有效
     *
     * @param key 键
     */
    private void validateKey(String key) {
        if (!StringUtils.hasText(key)) {
            throw new IllegalArgumentException("Redis key不能为空");
        }
    }

    /**
     * 验证Hash key是否有效
     *
     * @param hashKey 哈希键
     */
    private void validateHashKey(String hashKey) {
        if (!StringUtils.hasText(hashKey)) {
            throw new IllegalArgumentException("Redis hash key不能为空");
        }
    }
}
