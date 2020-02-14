package junit.alg.consistenceHash;
import	java.util.HashSet;
import	java.util.SortedMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.TreeMap;


@Slf4j
public class TreeMapTest {

    @Test
    public void test() {

        TreeMap treeMap=new TreeMap();

        for (int i = 0; i < 10; i++) {
            treeMap.put(i*2,"xx"+i*2);
        }
        log.info("map:{}",treeMap);


        log.info("7: {}",treeMap.lowerEntry(7));
        log.info("6: {}",treeMap.lowerEntry(6));

        log.info("7: {}",treeMap.floorEntry(7));
        log.info("6: {}",treeMap.floorEntry(6));


        log.info("firstEntry: {}",treeMap.firstEntry());
        log.info("lastEntry: {}",treeMap.lastEntry());


        SortedMap tailMap=treeMap.tailMap(7);
        log.info("tailMap: {}",tailMap);


        log.info("tailMapFirst: {}",tailMap.firstKey());
        log.info("tailLastKey: {}",tailMap.lastKey());

        tailMap.put(30,"30xx");
        log.info("tailMap: {}",tailMap);
        log.info("origin: {}",treeMap);



        HashSet s=new HashSet();
        s.retainAll(new HashSet());

    }
}
