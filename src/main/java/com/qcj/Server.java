package com.qcj;

/**
 * 模拟服务器
 */
public class Server {
    public String ip;//ip
    public int weight;//权重

    public Server() {
    }

    public Server(String ip, int weight) {
        this.ip = ip;
        this.weight = weight;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
