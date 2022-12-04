package com.donghao.dfs.namenode;

import com.donghao.dfs.namenode.server.NameNodeRpcServer;
import com.donghao.dfs.namenode.tools.FSNameSystem;

/**
 * 核心启动类
 */
public class NameNode {
  private FSNameSystem nameSystem;

  /**
   * 响应请求
   */
  private NameNodeRpcServer rpcServer;

  /**
   * NameNode 是否正在运行
   */
  private volatile Boolean shouldRun;

  public NameNode() {
    this.shouldRun = true;
  }

  private void initialize() {
    this.nameSystem = new FSNameSystem();
    this.rpcServer = new NameNodeRpcServer(this.nameSystem);
    this.rpcServer.start();
  }

  private void run() {
    while (shouldRun) {
      try {
        Thread.sleep(10 * 1000L);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  public static void main(String[] args) {
    NameNode nameNode = new NameNode();
    nameNode.initialize();
    nameNode.run();
  }
}
