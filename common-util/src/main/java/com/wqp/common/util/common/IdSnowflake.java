package com.wqp.common.util.common;

import org.apache.commons.codec.digest.DigestUtils;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 雪花算法生成ID工具类
 */
public final class IdSnowflake {
    private final long startTime = 1488697858401L;
    private long workerIdBits = 5L;
    private long datacenterIdBits = 5L;
    private long maxWorkerId;
    private long maxDatacenterId;
    private long sequenceBits;
    private long workerIdLeftShift;
    private long datacenterIdLeftShift;
    private long timestampLeftShift;
    private long sequenceMask;
    private long workerId;
    private long sequence;
    private long lastTimestamp;
    private static IdSnowflake local;

    public IdSnowflake(long workerId) {
        this.maxWorkerId = ~(-1L << (int)this.workerIdBits);
        this.maxDatacenterId = ~(-1L << (int)this.datacenterIdBits);
        this.sequenceBits = 12L;
        this.workerIdLeftShift = this.sequenceBits;
        this.datacenterIdLeftShift = this.workerIdBits + this.workerIdLeftShift;
        this.timestampLeftShift = this.datacenterIdBits + this.datacenterIdLeftShift;
        this.sequenceMask = (long)(~(-1 << (int)this.sequenceBits));
        this.sequence = 0L;
        this.lastTimestamp = -1L;
        if (workerId >= 0L && workerId <= this.maxWorkerId) {
            this.workerId = workerId;
        } else {
            throw new IllegalArgumentException(String.format("workerId[%d] is less than 0 or greater than maxWorkerId[%d].", workerId, this.maxWorkerId));
        }
    }

    public IdSnowflake(long workerId, long datacenterId) {
        this.maxWorkerId = ~(-1L << (int)this.workerIdBits);
        this.maxDatacenterId = ~(-1L << (int)this.datacenterIdBits);
        this.sequenceBits = 12L;
        this.workerIdLeftShift = this.sequenceBits;
        this.datacenterIdLeftShift = this.workerIdBits + this.workerIdLeftShift;
        this.timestampLeftShift = this.datacenterIdBits + this.datacenterIdLeftShift;
        this.sequenceMask = (long)(~(-1 << (int)this.sequenceBits));
        this.sequence = 0L;
        this.lastTimestamp = -1L;
        if (workerId >= 0L && workerId <= this.maxWorkerId) {
            if (datacenterId >= 0L && datacenterId <= this.maxDatacenterId) {
                this.workerId = workerId;
            } else {
                throw new IllegalArgumentException(String.format("datacenterId[%d] is less than 0 or greater than maxDatacenterId[%d].", datacenterId, this.maxDatacenterId));
            }
        } else {
            throw new IllegalArgumentException(String.format("workerId[%d] is less than 0 or greater than maxWorkerId[%d].", workerId, this.maxWorkerId));
        }
    }

    public synchronized Long nextId(Class<?> clazz) {
        String md5 = DigestUtils.md5Hex(clazz.getName());
        int datacenterId = Math.abs(Integer.parseInt(md5.substring(0, 1), 16) + Integer.parseInt(md5.substring(30, 31), 16) - 1);
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
        } else {
            if (timestamp == this.lastTimestamp) {
                this.sequence = this.sequence + 1L & this.sequenceMask;
                if (this.sequence == 0L) {
                    timestamp = this.tilNextMillis();
                    this.sequence = 0L;
                }
            } else {
                this.sequence = 0L;
            }

            this.lastTimestamp = timestamp;
            return timestamp - 1488697858401L << (int)this.timestampLeftShift | (long)(datacenterId << (int)this.datacenterIdLeftShift) | this.workerId << (int)this.workerIdLeftShift | this.sequence;
        }
    }

    protected long tilNextMillis() {
        long timestamp = this.timeGen();
        if (timestamp <= this.lastTimestamp) {
            timestamp = this.timeGen();
        }

        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public static synchronized IdSnowflake getLocalInstance() {
        if (local == null) {
            int workId = 1;

            try {
                InetAddress ia = InetAddress.getLocalHost();
                String hostAddress = ia.getHostAddress();
                hostAddress = DigestUtils.md5Hex(hostAddress);
                workId = Integer.parseInt(hostAddress.substring(0, 1), 16) + Integer.parseInt(hostAddress.substring(30, 31), 16);
            } catch (UnknownHostException var3) {
                var3.printStackTrace();
            }

            local = new IdSnowflake((long)workId);
        }

        return local;
    }
}
