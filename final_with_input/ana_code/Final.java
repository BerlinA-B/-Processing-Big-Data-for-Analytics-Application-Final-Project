import java.net.URI;

import org.apache.hadoop.fs.Path;

import org.apache.hadoop.filecache.DistributedCache;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.NullWritable;

import org.apache.hadoop.mapreduce.Job;

import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class Final {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: Final <input path> <output path");
            System.exit(-1);
        }

        Configuration conf = new Configuration();
        DistributedCache.addCacheFile(new URI("/AFINN-111.txt"), conf);

        Job job = new Job();
        job.setJarByClass(Final.class);
        job.setJobName("Twitter Sentiment Analysis");

        FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

        job.setMapperClass(FinalMapper.class);
        job.setReducerClass(FinalReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        //job.setNumReduceTasks(1);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
