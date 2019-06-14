/********************************************
 * 文件名称: ConsistentHash.java
 * 系统名称: 
 * 模块名称: 
 * 软件版权: 北京合融科技有限公司
 * 功能说明: 
 * 系统版本: 
 * 开发人员: lt
 * 开发时间: 2017-1-19
 * 审核人员:
 * 相关文档:
 * 修改记录: 修改日期    修改人员    修改说明
 *             .----.
 *          _.'__    `.
 *      .--(^)(^^)---/!\
 *    .' @          /!!!\
 *    :         ,    !!!!
 *     `-..__.-' _.-\!!!/
 *           `;_:    `"'
 *          .'"""""`.
 *          /,  lt ,\\
 *         // 嘿嘿嘿嘿      \\
 *         `-._______.-'
 *         ___`. | .'___
 *        (______|______)
 *********************************************/
package cn.com.afcat.shcpemonitor.common.cache.redis;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;

/**
 * 一致性Hash算法
 *
 */
public class ConsistentHash<T> {

    private TreeMap<Long, T> virtualNodes; // 虚拟节点
    private List<T> nodes; // 真实机器节点
    private final int NODE_NUM = 100; // 每个机器节点关联的虚拟节点个数

    public ConsistentHash(List<T> nodes) {
        super();
        this.nodes = nodes;
        init();
    }

    private void init() { // 初始化一致性hash环
        virtualNodes = new TreeMap<Long, T>();
        for (int i = 0; i != nodes.size(); ++i) { // 每个真实机器节点都需要关联虚拟节点
            final T shardInfo = nodes.get(i);
            for (int n = 0; n < NODE_NUM; n++)
                // 一个真实机器节点关联NODE_NUM个虚拟节点
                virtualNodes.put(hash("SHARD-" + i + "-NODE-" + n), shardInfo);
        }
    }

    public T get(String key) {
        SortedMap<Long, T> tail = virtualNodes.tailMap(hash(key)); // 沿环的顺时针找到一个虚拟节点
        if (tail.size() == 0) {
            return virtualNodes.get(virtualNodes.firstKey());
        }
        return tail.get(tail.firstKey()); // 返回该虚拟节点对应的真实机器节点的信息
    }

    /**
     * MurMurHash算法，是非加密HASH算法，性能很高，
     * 比传统的CRC32,MD5，SHA-1（这两个算法都是加密HASH算法，复杂度本身就很高，带来的性能上的损害也不可避免）
     * 等HASH算法要快很多，而且据说这个算法的碰撞率很低. http://murmurhash.googlepages.com/
     */
    private Long hash(String key) {
        ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
        int seed = 0x1234ABCD;

        ByteOrder byteOrder = buf.order();
        buf.order(ByteOrder.LITTLE_ENDIAN);

        long m = 0xc6a4a7935bd1e995L;
        int r = 47;

        long h = seed ^ (buf.remaining() * m);

        long k;
        while (buf.remaining() >= 8) {
            k = buf.getLong();

            k *= m;
            k ^= k >>> r;
            k *= m;

            h ^= k;
            h *= m;
        }
        if (buf.remaining() > 0) {
            ByteBuffer finish = ByteBuffer.allocate(8).order(
                    ByteOrder.LITTLE_ENDIAN);
            // for big-endian version, do this first:
            // finish.position(8-buf.remaining());
            finish.put(buf).rewind();
            h ^= finish.getLong();
            h *= m;
        }

        h ^= h >>> r;
        h *= m;
        h ^= h >>> r;
        buf.order(byteOrder);
        return h;
    }
}
