package com.donghao.dfs.namenode.log;

/**
 * 负责管理editsLog日志写入磁盘的核心组件
 * 
 * @author donghao.wu
 */
public class FSEditlog {
  /**
   * 记录editsLog日志
   * 
   * @param log 日志内容
   */
  public void logEdits(String log) {
    System.out.println(log);
  }
}
