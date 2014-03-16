package com.dota2trade.test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;

public class MapReduce{
    protected final static String TABLE_NAME = "literature";
    protected final static String FAMILY = "literature_info";
    protected final static String COLUMN = "title";

    public static class InvertedIndexMap extends TableMapper<Text, Text> {
        private final Text countOne = new Text();
        private final Text reusableText = new Text();

        protected void map(ImmutableBytesWritable key, Result value, Context context)
                throws IOException, InterruptedException {
            byte[] bytes = value.getValue(Bytes.toBytes(FAMILY), Bytes.toBytes(COLUMN));
            String str = Bytes.toString(bytes);
            String[] strs = str.split(" ");
            for(int i=0;i<strs.length;i++){
                reusableText.set(strs[i]+":"+new String(value.getRow()));
                countOne.set("1");
                context.write(reusableText, countOne);
            }

        }
    }

    public static class InvertedIndexCombiner extends Reducer<Text,Text,Text,Text>{

        Text info = new Text();

        public void reduce(Text key, Iterable<Text> values,Context contex)
                throws IOException, InterruptedException {
            int sum = 0;
            for(Text value : values){
                sum += Integer.parseInt(value.toString());
            }

            int splitIndex = key.toString().indexOf(":");
            //重新设置value值由（rowkey+:词频组成）
            info.set(key.toString().substring(splitIndex+1) +":"+ sum);
            //重新设置key值为单词
            key.set(key.toString().substring(0,splitIndex));
            contex.write(key, info);
        }
    }

    public static class InvertedIndexReduce extends Reducer<Text,Text,Text,Text>{

        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values,Context contex)
                throws IOException, InterruptedException {
            //生成文档列表
            String fileList = new String();
            for (Text value : values) {
                fileList += value.toString()+";";
            }
            result.set(fileList);
            contex.write(key, result);
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        Job job = new Job(new Configuration(), "InvertedIndex");
        job.setJarByClass(MapReduce.class);

        // configure input
        job.setInputFormatClass(TableInputFormat.class);
        job.setMapperClass(InvertedIndexMap.class);

        Configuration conf = job.getConfiguration();
        HBaseConfiguration.merge(conf, HBaseConfiguration.create(conf));
        TableMapReduceUtil.addDependencyJars(job);

        conf.set(TableInputFormat.INPUT_TABLE, TABLE_NAME);
        conf.set(TableInputFormat.SCAN_COLUMNS, FAMILY + ":" + COLUMN);


        // configure mapper and reducer
        job.setCombinerClass(InvertedIndexCombiner.class);
        job.setReducerClass(InvertedIndexReduce.class);

        // configure output
        FileOutputFormat.setOutputPath(job, new Path("./out/"));
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        System.exit(job.waitForCompletion(true)?0:1);


    }
}
