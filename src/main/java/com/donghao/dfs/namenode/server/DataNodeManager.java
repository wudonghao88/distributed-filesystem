package com.donghao.dfs.namenode.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这个组件，就是负责管理集群里的所有的datanode的
 * 
 * @author donghao.wu
 *
 */
public class DataNodeManager {
  public DataNodeManager() {
    DataNodeAliveMonitor dataNodeAliveMonitor = new DataNodeAliveMonitor();
    dataNodeAliveMonitor.start();
  }

  /**
   * 
   */
  private static final Long TTL = 5 * 60 * 1000L;

  /**
   * 集群中所有datanode
   */
  private Map<String, DataNodeInfo> datanodes = new ConcurrentHashMap<String, DataNodeInfo>();

  /**
   * datanode进行注册
   * 
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
   * 
   * @param ip
   * @param hostname
   * @return
   */
  public Boolean heartbeat(String ip, String hostname) {
    DataNodeInfo datanode = datanodes.get(ip + "-" + hostname);
    datanode.setLatestHeartbeatTime(System.currentTimeMillis());
    return true;
  }

  /**
   * datanode存活检查
   */
  class DataNodeAliveMonitor extends Thread {
    /**
     * If this thread was constructed using a separate
     * {@code Runnable} run object, then that
     * {@code Runnable} object's {@code run} method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of {@code Thread} should override this method.
     *
     * @see #start()
     * @see #stop()
     * @see #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
      while (true) {
        try {
          List<String> toRemoveKeys = new ArrayList<>();
          for (Map.Entry<String, DataNodeInfo> value : datanodes.entrySet()) {
            if (System.currentTimeMillis() - value.getValue().getLatestHeartbeatTime() > TTL) {
              toRemoveKeys.add(value.getKey());
            }
          }
          if (toRemoveKeys.isEmpty()) {
            for (String toRemoveKey : toRemoveKeys) {
              datanodes.remove(toRemoveKey);
            }
          }
          Thread.sleep(30 * 1000L);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    }

  }
}
