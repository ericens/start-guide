设计思路：

1. store 里面有 queue;  内存维护，体现为Store里面的属性：ConcurrentHashMap<String, Queue>[] queueMaps，数组里的一个element是一个map,对应一个文件, 一个map里面多queue;
2. queue 里面有 block;  内存维护，体现为QueueIndex类，List<BlockInfo> blockInfos=new ArrayList<>();但是 xjf没有对这些 索引信息进行封装，所以看起来有些乱。原理和QueueIndex一样。
    主要有：
         private long pyOffsets[] = new long[size];
         private int firstOffsetInBlock[] = new int[size];
    block 里面有 msgs;   内存维护block的元数据，体现为BlockInfo类，在包含在QueueIndex类；磁盘维护真正的消息。

3. 主要优化：
    1. 发现buffer 不够存储消息时，copy到另外一个flushBuffer, 然后异步从flushBuffer提交磁盘。线程继续积累消息到buffer
    2. 多个queueName的hash, 分到不同的文件中。一个文件多个queue.