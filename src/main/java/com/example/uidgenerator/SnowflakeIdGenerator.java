package com.example.uidgenerator;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

public class SnowflakeIdGenerator {
	private final static LoadingCache<Long, SnowflakeIdGenerator> idGenerators = CacheBuilder
			.newBuilder().expireAfterAccess(3600, TimeUnit.SECONDS)
			.removalListener(new RemovalListener<Long, SnowflakeIdGenerator>() {
				@Override
				public void onRemoval(
						RemovalNotification<Long, SnowflakeIdGenerator> entry) {
					entry.getValue().destroy();
				}
			}).build(new CacheLoader<Long, SnowflakeIdGenerator>() {
				@Override
				public SnowflakeIdGenerator load(Long nodeId) throws Exception {
					System.out.println("nodeId:"+nodeId);
					SnowflakeIdGenerator idGen = new SnowflakeIdGenerator(
							nodeId);
					idGen.init();
					return idGen;
				}
			});
	private static long macAddr = 0;
	static {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			for (byte temp : mac) {
				macAddr = (macAddr << 8) | ((int) temp & 0xFF);
			}
		} catch (Exception e) {
			macAddr = System.currentTimeMillis();
		}
	}

			 /**
			  * Gets an {@link SnowflakeIdGenerator} instance. 
			  *  
			  * @return 
			  */ 
			 public static SnowflakeIdGenerator getInstance() { 
			  return getInstance(macAddr); 
			 } 
			 
			 /**
			  * Gets an {@link SnowflakeIdGenerator} instance for a node. 
			  *  
			  * @param nodeId 
			  * @return 
			  */ 
			 public static SnowflakeIdGenerator getInstance(long nodeId) { 
			  try { 
			   return idGenerators.get(nodeId); 
			  } catch (ExecutionException e) { 
			   return null; 
			  } 
			 } 
			 
			 private final static long MASK_TIMESTAMP_MINI = 0x1FFFFFFFFFFL; // 41 bits 
			 private final static long MASK_NODE_ID_MINI = 0x0L; // 0 bits 
			 private final static long MASK_SEQUENCE_MINI = 0x7FL; // 7 bits 
			 private final static long MAX_SEQUENCE_MINI = 0x7FL; // 7 bits 
			 private final static long SHIFT_TIMESTAMP_MINI = 7L; 
			 private final static long SHIFT_NODE_ID_MINI = 7L; 
			 
			 private final static long MASK_TIMESTAMP_48 = 0xFFFFFFFFL; // 32 bits 
			 private final static long MASK_NODE_ID_48 = 0x7L; // 3 bits 
			 private final static long MASK_SEQUENCE_48 = 0x1FFFL; // 13 bits 
			 private final static long MAX_SEQUENCE_48 = 0x1FFFL; // 13 bits 
			 private final static long SHIFT_TIMESTAMP_48 = 16L; 
			 private final static long SHIFT_NODE_ID_48 = 13L; 
			 
			 private final static long MASK_TIMESTAMP_64 = 0x1FFFFFFFFFFL; // 41 bits 
			 private final static long MASK_NODE_ID_64 = 0x3FFL; // 10 bits 
			 private final static long MASK_SEQUENCE_64 = 0x1FFFL; // 13 bits 
			 private final static long MAX_SEQUENCE_64 = 0x1FFFL; // 13 bits 
			 private final static long SHIFT_TIMESTAMP_64 = 23L; 
			 private final static long SHIFT_NODE_ID_64 = 13L; 
			 // private final static long TIMESTAMP_EPOCH = 1330534800000L; // 1-Mar-2012 
			 // GMT+7 
			 // public final static long TIMESTAMP_EPOCH = 1362070800000L; // 1-Mar-2013 
			 // GMT+7 
			 public final static long TIMESTAMP_EPOCH = 1393632000000L; // 1-Mar-2014 GMT 
			 
			 private final static long MASK_NODE_ID_128 = 0xFFFFFFFFFFFFL; // 48 bits 
			 private final static long MASK_SEQUENCE_128 = 0xFFFF; // 16 bits 
			 private final static long MAX_SEQUENCE_128 = 0xFFFF; // 16 bits 
			 private final static long SHIFT_TIMESTAMP_128 = 64L; 
			 private final static long SHIFT_NODE_ID_128 = 16L; 
			 
			 private long nodeId; 
			 private long template48, template64, templateMini; 
			 private BigInteger template128; 
			 private AtomicLong sequenceMillisec = new AtomicLong(); 
			 private AtomicLong sequenceSecond = new AtomicLong(); 
			 private AtomicLong lastTimestampMillisec = new AtomicLong(); 
			 private AtomicLong lastTimestampSecond = new AtomicLong(); 
			 
			 /**
			  * Constructs a new {@link SnowflakeIdGenerator} instance with specified 
			  * node id. 
			  *  
			  * @param nodeId 
			  */ 
			 protected SnowflakeIdGenerator(long nodeId) { 
			  this.nodeId = nodeId; 
			 } 
			 
			 protected void init() { 
			  this.templateMini = (this.nodeId & MASK_NODE_ID_MINI) << SHIFT_NODE_ID_MINI; 
			  this.template64 = (this.nodeId & MASK_NODE_ID_64) << SHIFT_NODE_ID_64; 
			  this.template48 = (this.nodeId & MASK_NODE_ID_48) << SHIFT_NODE_ID_48; 
			  this.template128 = BigInteger 
			    .valueOf((this.nodeId & MASK_NODE_ID_128) << SHIFT_NODE_ID_128); 
			 } 
			 
			 protected void destroy() { 
			  // EMPTY 
			 } 
			 
			 /**
			  * Waits till clock moves to the next millisecond. 
			  *  
			  * @param currentMillisec 
			  * @return the "next" millisecond 
			  */ 
			 public static long waitTillNextMillisec(long currentMillisec) { 
			  long nextMillisec = System.currentTimeMillis(); 
			  for (; nextMillisec <= currentMillisec; nextMillisec = System 
			    .currentTimeMillis()) { 
			   Thread.yield(); 
			  } 
			  return nextMillisec; 
			 } 
			 
			 /**
			  * Waits till clock moves to the next second. 
			  *  
			  * @param currentSecond 
			  * @return the "next" second 
			  */ 
			 public static long waitTillNextSecond(long currentSecond) { 
			  long nextSecond = System.currentTimeMillis() / 1000L; 
			  for (; nextSecond <= currentSecond; nextSecond = System 
			    .currentTimeMillis() / 1000) { 
			   try { 
			    Thread.sleep(10); 
			   } catch (InterruptedException e) { 
			   } 
			  } 
			  return nextSecond; 
			 } 
			 
			 /**
			  * Waits till clock moves to the next tick. 
			  *  
			  * @param currentTick 
			  * @param tickSize 
			  *            tick size in milliseconds 
			  * @return 
			  */ 
			 public static long waitTillNextTick(long currentTick, long tickSize) { 
			  long nextBlock = System.currentTimeMillis() / tickSize; 
			  for (; nextBlock <= currentTick; nextBlock = System.currentTimeMillis() 
			    / tickSize) { 
			   try { 
			    Thread.sleep(10); 
			   } catch (InterruptedException e) { 
			   } 
			  } 
			  return nextBlock; 
			 } 
			 
			 /* tiny id */ 
			 /**
			  * Extracts the (UNIX) timestamp from a tiny id. 
			  *  
			  * @param idTiny 
			  * @return the UNIX timestamp (milliseconds) 
			  */ 
			 public static long extractTimestampTiny(long idTiny) { 
			  final long division = 10000L; 
			  final long seqBits = 16L; 
			  final long threshold = 0x800000000L; 
			  long timestamp = idTiny > threshold ? idTiny >>> seqBits : idTiny; 
			  return timestamp * division + TIMESTAMP_EPOCH; 
			 } 
			 
			 /**
			  * Generates a tiny id (various bit long, node is not accounted). 
			  *  
			  * Format: <41-bit: timestamp><0 or 16bit:sequence number> 
			  *  
			  * Where timestamp is in second, minus the epoch. 
			  *  
			  * Note: the generated id is NOT in order! 
			  *  
			  * @return 
			  */ 
			 synchronized public long generateIdTiny() { 
			  final long division = 10000L; // block 10000 ms 
			  final long seqBits = 16L; 
			  final long maxSeqTiny = 0xFFFFL; // 16 bits 
			 
			  long timestamp = System.currentTimeMillis() / division; 
			  long sequence = 0; 
			  if (timestamp == this.lastTimestampSecond.get()) { 
			   // increase sequence 
			   sequence = this.sequenceSecond.incrementAndGet(); 
			   if (sequence > maxSeqTiny) { 
			    // reset sequence 
			    this.sequenceSecond.set(0); 
			    waitTillNextTick(timestamp, division); 
			    return generateIdTiny(); 
			   } 
			  } else { 
			   // reset sequence 
			   this.sequenceSecond.set(sequence); 
			   this.lastTimestampSecond.set(timestamp); 
			  } 
			  timestamp -= TIMESTAMP_EPOCH / division; 
			  long result = timestamp; 
			  if (sequence != 0) { 
			   result = (result << seqBits) | sequence; 
			  } 
			  return result; 
			 } 
			 
			 /* tiny id */ 
			 
			 /* 48-bit id */ 
			 
			 /**
			  * Extracts the (UNIX) timestamp from a 48-bit id. 
			  *  
			  * @param id48 
			  * @return the UNIX timestamp (milliseconds) 
			  */ 
			 public static long extractTimestamp48(long id48) { 
			  long timestamp = (id48 >>> SHIFT_TIMESTAMP_48) + TIMESTAMP_EPOCH; 
			  return timestamp; 
			 } 
			 
			 /**
			  * Generates a 48-bit id. 
			  *  
			  * Format: <32-bit: timestamp><3-bit: node id><13 bit: sequence number> 
			  *  
			  * Where timestamp is in millisec, minus the epoch. 
			  *  
			  * @return 
			  */ 
			 synchronized public long generateId48() { 
			  long timestamp = System.currentTimeMillis(); 
			  long sequence = 0; 
			  if (timestamp == this.lastTimestampMillisec.get()) { 
			   // increase sequence 
			   sequence = this.sequenceMillisec.incrementAndGet(); 
			   if (sequence > MAX_SEQUENCE_48) { 
			    // reset sequence 
			    this.sequenceMillisec.set(0); 
			    waitTillNextMillisec(timestamp); 
			    return generateId48(); 
			   } 
			  } else { 
			   // reset sequence 
			   this.sequenceMillisec.set(sequence); 
			   this.lastTimestampMillisec.set(timestamp); 
			  } 
			  timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_48; 
			  long result = timestamp << SHIFT_TIMESTAMP_48 | template48 
			    | (sequence & MASK_SEQUENCE_48); 
			  return result; 
			 } 
			 
			 /* 48-bit id */ 
			 
			 /* mini id */ 
			 /**
			  * Extracts the (UNIX) timestamp from a mini id. 
			  *  
			  * @param idMini 
			  * @return the UNIX timestamp (milliseconds) 
			  */ 
			 public static long extractTimestampMini(long idMini) { 
			  long timestamp = (idMini >>> SHIFT_TIMESTAMP_MINI) + TIMESTAMP_EPOCH; 
			  return timestamp; 
			 } 
			 
			 /**
			  * Generates a mini id (48 bit long, node is not accounted). 
			  *  
			  * Format: <41-bit: timestamp><0-bit: node id><7 bit: sequence number> 
			  *  
			  * Where timestamp is in millisec, minus the epoch. 
			  *  
			  * @return 
			  */ 
			 synchronized public long generateIdMini() { 
			  long timestamp = System.currentTimeMillis(); 
			  long sequence = 0; 
			  if (timestamp == this.lastTimestampMillisec.get()) { 
			   // increase sequence 
			   sequence = this.sequenceMillisec.incrementAndGet(); 
			   if (sequence > MAX_SEQUENCE_MINI) { 
			    // reset sequence 
			    this.sequenceMillisec.set(0); 
			    waitTillNextMillisec(timestamp); 
			    return generateIdMini(); 
			   } 
			  } else { 
			   // reset sequence 
			   this.sequenceMillisec.set(sequence); 
			   this.lastTimestampMillisec.set(timestamp); 
			  } 
			  timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_MINI; 
			  long result = timestamp << SHIFT_TIMESTAMP_MINI | templateMini 
			    | (sequence & MASK_SEQUENCE_MINI); 
			  return result; 
			 } 
			 
			 /* mini id */ 
			 
			 /* 64-bit id */ 
			 /**
			  * Extracts the (UNIX) timestamp from a 64-bit id. 
			  *  
			  * @param id64 
			  * @return the UNIX timestamp (milliseconds) 
			  */ 
			 public static long extractTimestamp64(long id64) { 
			  // use >>> for unsigned number 
			  long timestamp = (id64 >>> SHIFT_TIMESTAMP_64) + TIMESTAMP_EPOCH; 
			  return timestamp; 
			 } 
			 
			 /**
			  * Generates a 64-bit id. 
			  *  
			  * Format: <41-bit: timestamp><10-bit: node id><13 bit: sequence number> 
			  *  
			  * Where timestamp is in millisec, minus the epoch. 
			  *  
			  * @return 
			  */ 
			 synchronized public long generateId64() { 
			  long timestamp = System.currentTimeMillis(); 
			  long sequence = 0; 
			  if (timestamp == this.lastTimestampMillisec.get()) { 
			   // increase sequence 
			   sequence = this.sequenceMillisec.incrementAndGet(); 
			   if (sequence > MAX_SEQUENCE_64) { 
			    // reset sequence 
			    this.sequenceMillisec.set(0); 
			    waitTillNextMillisec(timestamp); 
			    return generateId64(); 
			   } 
			  } else { 
			   // reset sequence 
			   this.sequenceMillisec.set(sequence); 
			   this.lastTimestampMillisec.set(timestamp); 
			  } 
			  timestamp = (timestamp - TIMESTAMP_EPOCH) & MASK_TIMESTAMP_64; 
			  long result = timestamp << SHIFT_TIMESTAMP_64 | template64 
			    | (sequence & MASK_SEQUENCE_64); 
			  return result; 
			 } 
			 
			 /* 64-bit id */ 
			 
			 /* 128-bit id */ 
			 
			 /**
			  * Extracts the (UNIX) timestamp from a 128-bit id. 
			  *  
			  * @param id128 
			  * @return the UNIX timestamp (milliseconds) 
			  */ 
			 public static long extractTimestamp128(BigInteger id128) { 
			  BigInteger result = id128.shiftRight((int) SHIFT_TIMESTAMP_128); 
			  return result.longValue(); 
			 } 
			 
			 /**
			  * Generates a 128-bit id. 
			  *  
			  * Format: <64-bit: timestamp><48-bit: node id><16 bit: sequence number> 
			  *  
			  * Where timestamp is in millisec. 
			  *  
			  * @return 
			  */ 
			 synchronized public BigInteger generateId128() { 
			  long timestamp = System.currentTimeMillis(); 
			  long sequence = 0; 
			  if (timestamp == this.lastTimestampMillisec.get()) { 
			   // increase sequence 
			   sequence = this.sequenceMillisec.incrementAndGet(); 
			   if (sequence > MAX_SEQUENCE_128) { 
			    // reset sequence 
			    this.sequenceMillisec.set(0); 
			    waitTillNextMillisec(timestamp); 
			    return generateId128(); 
			   } 
			  } else { 
			   // reset sequence 
			   this.sequenceMillisec.set(sequence); 
			   this.lastTimestampMillisec.set(timestamp); 
			  } 
			 
			  BigInteger biSequence = BigInteger 
			    .valueOf(sequence & MASK_SEQUENCE_128); 
			  BigInteger biResult = BigInteger.valueOf(timestamp); 
			  biResult = biResult.shiftLeft((int) SHIFT_TIMESTAMP_128); 
			  biResult = biResult.or(template128).or(biSequence); 
			  return biResult; 
			 } 
}
