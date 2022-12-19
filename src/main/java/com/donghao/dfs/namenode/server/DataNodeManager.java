package com.donghao.dfs.namenode.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个组件，就是负责管理集群里的所有的datanode的
 * @author donghao.wu
 *
 */
public class DataNodeManager {

  private Map<String, DataNodeInfo> datanodes =
          new ConcurrentHashMap<String, DataNodeInfo>();

  /**
   * datanode进行注册
   * @param ip
   * @param hostname
   */
  public Boolean register(String ip, String hostname) {
    DataNodeInfo datanode = new DataNodeInfo(ip, hostname);
    datanodes.put(ip + "-" + hostname, datanode);
    return true;
  }

  /**
   * datanode进行心跳
   * @param ip
   * @param hostname
   * @return
   */
  public Boolean heartbeat(String ip, String hostname) {
    DataNodeInfo datanode = datanodes.get(ip + "-" + hostname);
    datanode.setLatestHeartbeatTime(System.currentTimeMillis());
    return true;
  }

}
