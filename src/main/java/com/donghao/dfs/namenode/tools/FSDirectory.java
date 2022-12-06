package com.donghao.dfs.namenode.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 负责管理内存中的文件目录树的核心组件
 * 
 * @author donghoa.wu
 */
public class FSDirectory {
  /**
   * 文件目录树
   */
  private INodeDirectory dirTree;

  public FSDirectory() {
    this.dirTree = new INodeDirectory("/");
  }

  public synchronized INodeDirectory get() {
    return this.dirTree;
  }

  /**
   * 创建目录
   * 
   * @param path 目录路径 "usr/warehouse/hive"
   * @throws Exception
   */
  public void mkdir(String path) throws Exception {
    if (path == null || path == "") {
      System.out.println(this.getClass().getName() + ".mkdir:未创建目录，入参未传入数据。path=" + path);
      return;
    }
    String[] paths = path.split("/");
    synchronized (this.dirTree) {
      INodeDirectory parent = null;
      boolean flag = true;
      for (String splitedPath : paths) {
        if (splitedPath.trim().equals("")) {
          continue;
        }
        INodeDirectory dir = null;
        if (flag) {
          if (parent == null) {
            dir = findDirectory(this.dirTree, splitedPath);
          } else {
            dir = findDirectory(parent, splitedPath);
          }
        }
        if (dir != null) {
          parent = dir;
          continue;
        }
        if (dir == null && parent != null) {
          INodeDirectory child = new INodeDirectory(splitedPath);
          if (parent.getChildren() == null) {
            parent.setChildren(new ArrayList<>());
          }
          parent.addChild(child);
          parent = child;
          flag = false;
        }
        if (parent == null && dir == null) {
          INodeDirectory child = new INodeDirectory(splitedPath);
          if (this.dirTree.getChildren() == null) {
            this.dirTree.setChildren(new ArrayList<>());
          }
          this.dirTree.addChild(child);
          parent = child;
          flag = false;
        }
      }
    }
  }

  private INodeDirectory findDirectory(INodeDirectory dir, String path) {
    if (dir == null || dir.getChildren() == null || dir.getChildren().isEmpty()) {
      return null;
    }
    INodeDirectory resultDir = null;

    for (INode child : dir.getChildren()) {
      if (child instanceof INodeDirectory) {
        resultDir = (INodeDirectory) child;
        if (resultDir.getPath().equals(path)) {
          return resultDir;
        }
      }
    }
    return null;
  }

  /**
   * 文件目录树中的一个节点
   *
   * @author donghao.wu
   */
  private interface INode {

  }

  /**
   * 文件目录树的一个目录
   */
  private class INodeDirectory implements INode {
    private String path;

    private List<INode> children;

    public INodeDirectory() {
      children = new ArrayList<>();
    }

    public INodeDirectory(String path) {
      this.path = path;
    }

    public String getPath() {
      return path;
    }

    public void setPath(String path) {
      this.path = path;
    }

    public List<INode> getChildren() {
      return children;
    }

    /**
     * 
     * @param inode
     */
    public void addChild(INode inode) {
      this.children.add(inode);
    }

    public void setChildren(List<INode> children) {
      this.children = children;
    }

    @Override
    public String toString() {
      return "INodeDirectory{" +
          "path='" + path + '\'' +
          ", children=" + children +
          '}';
    }
  }

  /**
   * 文件目录树中的一个文件
   */
  private class INodeFile implements INode {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
