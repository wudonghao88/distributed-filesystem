package com.donghao.dfs.namenode;

import com.alibaba.fastjson.JSONObject;
import com.donghao.dfs.namenode.tools.FSDirectory;

public class Test {
  public static void main(String[] args) throws Exception {
    FSDirectory ds = new FSDirectory();
    ds.mkdir("/usr/a/b");
    System.out.println(JSONObject.toJSONString(FSDirectory.get()));
    ds.mkdir("/d/e/f");
    System.out.println(JSONObject.toJSONString(FSDirectory.get()));
    ds.mkdir("/d/e/h");
    System.out.println(JSONObject.toJSONString(FSDirectory.get()));
    ds.mkdir("/d/f/f");
    System.out.println(JSONObject.toJSONString(FSDirectory.get()));
  }
}
