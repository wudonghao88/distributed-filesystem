package com.donghao.dfs.namenode.log;

import java.util.LinkedList;
import java.util.List;

/**
 * 负责管理editsLog日志写入磁盘的核心组件
 * 
 * @author donghao.wu
 */
public class FSEditlog {
  /**
   * 當前线程的txid 序号
   */
  private long txIdSeq = 0L;

  /**
   * 双缓冲区
   */
  private DoubleBuffer editLogDoubleBuffer = new DoubleBuffer();

  /**
   * 记录editsLog日志
   * 
   * @param log 日志内容
   */
  public void logEdits(String content) {
    synchronized (this) {
      txIdSeq++;
      long txId = txIdSeq;
      EditLog editLog = new EditLog(txId, content);
      // 将diets log写入缓冲中，不是直接刷入磁盘文件
      editLogDoubleBuffer.write(editLog);
    }
  }

  /**
   * 双缓冲
   */
  private class DoubleBuffer {
    /**
     * 是专门用来承载线程写入edits log
     */
    private List<EditLog> currentBuffer = new LinkedList<EditLog>();

    /**
     * 专门用来数据同步磁盘中去的一块缓冲
     */
    private List<EditLog> syncBuffer = new LinkedList<EditLog>();

    /**
     * 将edits log 写到内存缓冲里去
     *
     * @param editLog edited
     */
    public void write(EditLog editLog) {
      currentBuffer.add(editLog);
    }

    /**
     * 交换两片缓冲区，为了同步内存数据到磁盘做准备
     */
    public void setReadyToSync() {
      List<EditLog> tmp = currentBuffer;
      currentBuffer = syncBuffer;
      syncBuffer = tmp;
    }

    /**
     * 将syncBuffer缓冲区中的数据刷入磁盘中
     */
    public void flush() {
      for (EditLog editLog : syncBuffer) {
        System.out.println("将edit log 写入磁盘。log:" + editLog);
        // 将用文件输出流写入磁盘文件中

      }
      syncBuffer.clear();
    }
  }

  public class EditLog {
    long txId;

    String content;

    public EditLog(long txId, String content) {
      this.txId = txId;
      this.content = content;
    }

    public long getTxId() {
      return txId;
    }

    public void setTxId(long txId) {
      this.txId = txId;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }
  }
}
