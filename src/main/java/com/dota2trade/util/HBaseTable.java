package com.dota2trade.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ljj
 * Date: 13-9-17
 * Time: 下午7:08
 * To change this template use File | Settings | File Templates.
 */
public class HBaseTable {

    static Configuration cfg = HBaseConfiguration.create();
    static {
        cfg.set("hbase.zookeeper.quorum", "127.0.0.1");
        cfg.set("hbase.zookeeper.property.clientPort", "2181");
    }

    /**
     *
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {

        HBaseAdmin admin = new HBaseAdmin(cfg);
        if (admin.tableExists("webpage")) {
            System.out.println("table   Exists!!!");
        }
        else{
            HTableDescriptor tableDesc = new HTableDescriptor("webpage");
            tableDesc.addFamily(new HColumnDescriptor("fff"));
            admin.createTable(tableDesc);
            System.out.println("create table ok .");
        }
    }
}
