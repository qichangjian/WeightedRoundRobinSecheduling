package com.qcj;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权重轮询调度算法(WeightedRound-RobinScheduling)-Java实现
 */
public class WeightedRoundRobinScheduling {
    private int currentIndex = -1;// 上一次选择的服务器
    private int currentWeight = 0;// 当前调度的权值
    private int maxWeight = 0; // 最大权重
    private int gcdWeight = 0; //所有服务器权重的最大公约数
    private int serverCount = 0; //服务器数量
    private List<Server> serverList; //服务器集合

    //初始化
    public void init() {
        Server server1 = new Server("192.168.2.100", 3);//3
        Server server2 = new Server("192.168.2.101", 2);//2
        Server server3 = new Server("192.168.2.102", 6);//6
        Server server4 = new Server("192.168.2.103", 4);//4
        Server server5 = new Server("192.168.2.104", 1);//1
        serverList = new ArrayList<Server>();
        serverList.add(server1);
        serverList.add(server2);
        serverList.add(server3);
        serverList.add(server4);
        serverList.add(server5);

        currentIndex = -1;// 上一次选择的服务器
        currentWeight = 0;// 当前调度的权值
        serverCount = serverList.size(); //服务器数量
        maxWeight = getMaxWeightForServers(serverList);//返回最大权重值
        gcdWeight = getGCDForServers(serverList);//所有服务器权重的最大公约数
    }

    /**
     * 返回所有服务器中的最大权重
     */
    public static int getMaxWeightForServers(List<Server> serverList) {
        int w = 0;
        for (int i = 0; i < serverList.size() - 1; i++) {
            if (w == 0) {
                //w = Math.max(serverList.get(i).getWeight(),serverList.get(i+1).getWeight());
                w = Math.max(serverList.get(i).weight, serverList.get(i + 1).weight);
            } else {
                w = Math.max(w, serverList.get(i + 1).weight);
            }
        }
        return w;
    }

    /**
     * 返回所有服务器权重的最大公约数
     */
    private int getGCDForServers(List<Server> serverList) {
        int w = 0;
        for (int i = 0; i < serverList.size() - 1; i++) {
            if (w == 0) {
                w = gcd(serverList.get(i).weight, serverList.get(i + 1).weight);
            } else {
                w = gcd(w, serverList.get(i + 1).weight);
            }
        }
        System.out.println("最大公约数：" + w);
        return w;
    }

    /**
     * 返回最大公约数
     */
    private static int gcd(int a, int b) {
        BigInteger bigInteger1 = new BigInteger(String.valueOf(a));
        BigInteger bigInteger2 = new BigInteger(String.valueOf(b));
        BigInteger result = bigInteger1.gcd(bigInteger2);  //调用求最大公约数的方法
        return result.intValue();
    }

    public static void main(String[] args) {
        WeightedRoundRobinScheduling obj = new WeightedRoundRobinScheduling();
        obj.init();

        Map<String, Integer> countResult = new HashMap<String, Integer>();

        for (int i = 0; i < 100; i++) {//100个server的分配结果
            Server s = obj.GetServer();
            String log = "ip:" + s.ip + ";weight:" + s.weight;
            if (countResult.containsKey(log)) {//记录结果到countResult中
                countResult.put(log, countResult.get(log) + 1);
            } else {
                countResult.put(log, 1);
            }
            System.out.println(log);
        }

        for (Map.Entry<String, Integer> map : countResult.entrySet()) {
            System.out.println("服务器 " + map.getKey() + " 请求次数： " + map.getValue());
        }
    }

    /**
     * 算法流程：
     * 假设有一组服务器 S = {S0, S1, …, Sn-1}
     * 有相应的权重，变量currentIndex表示上次选择的服务器
     * 权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
     * 通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0
     */
    public Server GetServer() {
        while (true) {
            currentIndex = (currentIndex + 1) % serverCount;//轮循index
            System.out.println(currentWeight + "当前服务器：" + currentIndex + "-服务器数量:" +serverCount + "-所有服务器的最大公约数：" + gcdWeight + "-最大权重值：" + maxWeight);
            if (currentIndex == 0) {
                currentWeight = currentWeight - gcdWeight;//当前权重 - 所有服务器的最大公约数1
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0)
                        return null;
                }
            }
            if (serverList.get(currentIndex).weight >= currentWeight) {//当前list的权重如果大于 当前权重 返回该server
                return serverList.get(currentIndex);
            }
        }
    }
}
