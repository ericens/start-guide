package io.openmessaging.my;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QueueIndex {

    @Data
    @Builder
    static public class BlockInfo{
        Integer offset;
        Long pyOffset;
        /**
         * 可以不打印，转化json时候、序列化时候
         */
        transient Integer msgCount;
    }

    List<BlockInfo> blockInfos=new ArrayList<>();
}
