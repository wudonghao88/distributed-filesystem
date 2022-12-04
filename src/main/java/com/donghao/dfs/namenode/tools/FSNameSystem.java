package com.donghao.dfs.namenode.tools;

import com.donghao.dfs.namenode.log.FSEditlog;

/**
 * 负责管理元数据的核心组件
 * 
 * @author donghao.wu
 */
public class FSNameSystem {
  /**
   * 负责管理内存中的文件目录树的核心组件
   */
  private FSDirectory directory;

  /**
   * 负责管理editsLog日志的核心组件
   */
  private FSEditlog editLog;

  public FSNameSystem() {
    this.directory = new FSDirectory();
    this.editLog = new FSEditlog();
  }

  /**
   * 创建目录
   * 
   * @param path 目录路径
   * @return 是否创建成功
   */
  public Boolean mkdir(String path) throws Exception {
    directory.mkdir(path);
    editLog.logEdits("创建了一个目录：" + path);
    return true;
  }
}
