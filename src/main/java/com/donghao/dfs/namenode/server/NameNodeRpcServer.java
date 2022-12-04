package com.donghao.dfs.namenode.server;

import com.donghao.dfs.namenode.tools.FSNameSystem;

/**
 * namenoderpc接口
 */
public class NameNodeRpcServer {
  private FSNameSystem nameSystem;

  public NameNodeRpcServer(FSNameSystem nameSystem) {
    this.nameSystem = nameSystem;
  }

  public Boolean mkdir(String path) throws Exception {
    return true;
  }

  public void start() {
    System.out.println("开始监听指定的端口号,处理发送过来的请求");
  }
}
