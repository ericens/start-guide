package junit.alg.consistenceHash;

import com.alibaba.fastjson.JSON;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;

import java.util.*;

@Slf4j
@NoArgsConstructor
public class ConsistentHash {
    private  int numberOfReplicas=0;// 节点的复制因子,实际节点个数 * numberOfReplicas = 虚拟节点个数

    //slotid,machineID
    private final SortedMap<Integer, String> circle = new TreeMap<Integer, String>();// 存储虚拟节点的hash值到真实节点的映射

    //machineID, slots
    private final HashMap<String, List> machine_nodeMap=new HashMap<>();

    //machineId,key,value
    private final HashMap<String,Map<String,Object>> machine_data=new HashMap();


    public ConsistentHash( int numberOfReplicas,
                           Collection<String> nodes) {
        this.numberOfReplicas = numberOfReplicas;
        for (String node : nodes){
            addMachine(node);
        }

        log.info("the circle map:{}", JSON.toJSONString(circle,true));
        log.info("the machine_nodeMap map:{}", JSON.toJSONString(machine_nodeMap,true));

    }


    public void addMachine(String machine){
        machine_nodeMap.putIfAbsent(machine,new ArrayList<>());
        for (int i = 0; i < numberOfReplicas; i++){
            int hashcode=RandomUtils.nextInt();
            circle.put(hashcode, machine);

            List nodeList=machine_nodeMap.get(machine);
            nodeList.add(hashcode);
        }
    }

    public void addNewMachine(String machine){
        machine_data.putIfAbsent(machine,new HashMap<>());

        for (int i = 0; i < numberOfReplicas; i++){
            int hashcode=RandomUtils.nextInt();

            //从old迁移 到新机器
            SortedMap<Integer,String> m=this.circle.tailMap(hashcode)==null?this.circle: this.circle.tailMap(hashcode);
            int nextHashcode=m.firstKey();
            String nextMachineId=circle.get(nextHashcode);

            Iterator<Map.Entry<String, Object>> iterator= this.machine_data.get(nextMachineId).entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, Object> entry=iterator.next();
                if(this.hash(entry.getKey()) > hashcode){
                    machine_data.get(machine).put(entry.getKey(),entry.getValue());
                    iterator.remove();
                }

            }
            circle.put(hashcode, machine);
            machine_nodeMap.putIfAbsent(machine,new ArrayList<>());
            List nodeList=machine_nodeMap.get(machine);
            nodeList.add(hashcode);

        }
    }
    public void removeMachine(String machine) {
        List list=this.machine_nodeMap.remove(machine);
        log.info("machine crash :{}, vnode:{}",machine,list);

        log.info("lost data:{}",this.machine_data.get(machine));



        Iterator<Map.Entry<Integer, String>> iterator=this.circle.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,String> entry=iterator.next();
            if(entry.getValue().equals(machine)){
                iterator.remove();
            }
        }

        log.info("remain circle {}",this.circle);




    }


    public int hash(Object key){
        return new StringBuilder().append(key.toString()).reverse().hashCode();// node 用String来表示,获得node在哈希环中的hashCode

    }

    /*
     * 获得一个最近的顺时针节点,根据给定的key 取Hash
     * 然后再取得顺时针方向上最近的一个虚拟节点对应的实际节点
     * 再从实际节点中取得 数据
     */


    public String getMachine(Object key) {
        if (circle.isEmpty()){
            return null;
        }
        int orginHash=key.hashCode();
        int hash=this.hash(key);
        if (!circle.containsKey(hash)) {//数据映射在两台虚拟机器所在环之间,就需要按顺时针方向寻找机器
            SortedMap<Integer, String> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        String o=circle.get(hash);
        log.info("key:{},origin:{},hash:{}, node:{} ",key,orginHash,hash,o);
        return o;
    }

    public long getSize() {
        return circle.size();
    }

    /*
     * 查看表示整个哈希环中各个虚拟节点位置
     */
    public void testBalance(){

        SortedSet<Integer> sortedSets= new TreeSet<Integer>(circle.keySet());//将获得的Key集合排序
        System.out.println("----each location 's distance are follows: ----");
        /*
         * 查看相邻两个hashCode的差值
         */
        Iterator<Integer> it = sortedSets.iterator();
        Iterator<Integer> it2 = sortedSets.iterator();
        if(it2.hasNext())
            it2.next();
        long keyPre, keyAfter;
        while(it.hasNext() && it2.hasNext()){
            keyPre = it.next();
            keyAfter = it2.next();
            System.out.println(keyAfter - keyPre);
        }
    }


    public static void main(String[] args) {

        Set<String> nodes = new HashSet<String>();
        nodes.add("A");
        nodes.add("B");
        nodes.add("C");



        ConsistentHash consistentHash = new ConsistentHash(4, nodes);
        consistentHash.testBalance();


        for (int i = 0; i <20 ; i++) {
            String key="apple"+i;
            String machineID=consistentHash.getMachine(key);
            consistentHash.machine_data.putIfAbsent(machineID,new HashMap<>());
            consistentHash.machine_data.get(machineID).put(key,RandomUtils.nextLong());
        }
        log.info("迁移前：dataInfo:{}",consistentHash.machine_data);
        consistentHash.addNewMachine("D");
        log.info("迁移后:newdataInfo:{}",consistentHash.machine_data);

        consistentHash.removeMachine("B");


    }

}
