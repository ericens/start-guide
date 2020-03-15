作者设计思路：

https://www.cnkirito.moe/mq-million-queue/


1. -- 稀疏索引适用于按块存储的消息，块内有序，适用于有序消息，索引量小，数据按照块进行存取。
2. 稠密索引是对全量的消息进行索引，适用于无序消息，索引量大，数据可以按条存取。
由于我使用 FIleChannel 进行读写，NIO 的读写可能走的正是 Direct IO，所以根本不会经过 PageCache 层。
测评环境中内存有限，在 IO 密集的情况下 PageCache 效果微乎其微。


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