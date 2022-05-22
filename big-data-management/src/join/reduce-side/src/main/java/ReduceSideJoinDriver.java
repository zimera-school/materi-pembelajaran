import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ReduceSideJoinDriver extends Configured implements Tool {

  private static final String DATA_SEPARATOR = ",";

  public int run(String[] args) throws Exception {

    Configuration configuration = new Configuration();

    configuration.set("mapreduce.output.textoutputformat.separator", DATA_SEPARATOR);

    Job job = Job.getInstance(configuration);

    job.setJobName("Reduce Side Join Mapreduce example using Java");

    job.setJarByClass(ReduceSideJoinDriver.class);

    // Map

    job.setMapOutputKeyClass(LongWritable.class);

    job.setMapOutputValueClass(Text.class);

    // Job

    job.setOutputKeyClass(LongWritable.class);

    job.setOutputValueClass(Text.class);

    job.setInputFormatClass(TextInputFormat.class);

    job.setOutputFormatClass(TextOutputFormat.class);

    job.setReducerClass(SalespersonDataReducer.class);

    MultipleInputs.addInputPath(job, new Path(args[0]), TextInputFormat.class, SalespersonFileMapper.class);

    MultipleInputs.addInputPath(job, new Path(args[1]), TextInputFormat.class, BranchFileMapper.class);

    FileOutputFormat.setOutputPath(job, new Path(args[2]));

    job.waitForCompletion(true);

    return 0;

  }

  public static void main(String[] args) throws Exception {

    if (args.length == 3) {

      int result = ToolRunner.run(new Configuration(), new ReduceSideJoinDriver(), args);

      if (0 == result) {
        System.out.println("Reduce Side Join Mapreduce example using Java Job Completed Successfully...");

      } else {
        System.out.println("Reduce Side Join Mapreduce example using Java Job Failed...");

      }
    } else {
      System.out.println("USAGE <InputPath1><InputPath2><OutputPath>");
    }

  }

}
