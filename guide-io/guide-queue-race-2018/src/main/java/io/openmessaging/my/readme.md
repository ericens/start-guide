设计思路：

1. store 里面有 queue;  内存维护，体现为Store里面的属性：Queue[] queues=new Queue[QUEUE_COUNT];
2. queue 里面有 block;  内存维护，体现为QueueIndex类，List<BlockInfo> blockInfos=new ArrayList<>()
3. block 里面有 msgs;   内存维护block的元数据，体现为BlockInfo类，在包含在QueueIndex类；磁盘维护真正的消息。

