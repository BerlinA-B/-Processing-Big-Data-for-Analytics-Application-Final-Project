import java.io.*;
import java.net.URI;
import java.util.HashMap;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FSDataInputStream;

import org.apache.hadoop.filecache.DistributedCache;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

public class FinalMapper extends Mapper<LongWritable, Text, Text, Text> {
    
    private URI[] f;
    private HashMap<String, String> dict = new HashMap<String, String>();
    
    @Override
    public void setup(Context context) throws IOException {
        f = DistributedCache.getCacheFiles(context.getConfiguration());
        Path path = new Path(f[0]);
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream in = fs.open(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        String line = "";
        while ( (line == reader.readLine()) != null) {
            String tokens[] = line.split("\t");
            dict.put(tokens[0], tokens[1]);
        }

        reader.close();
        in.close();
    }

    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        
        String line = value.toString();
        String tokens[] = line.split("\\n");
        String tweet;

        JSONParser jp = new JSONParser();
        
        try {
            for (int i = 0; i < tokens.length; i++) {
                JSONObject obj = (JSONObject) jp.parse(line);
                String id = (String) obj.get("id_str");
                String text = (String) obj.get("text");
                tweet = text;

                int count = 0;
                String words[] = tweet.split(" ");
                for (String word : words) {
                    if (dict.containsKey(word)) {
                        Integer cur = new Integer(dict.get(word));
                        count += cur;
                    }
                }
                context.write(new Text(id), new Text(text) + " " + new Text(Integer.toString(count)));
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
