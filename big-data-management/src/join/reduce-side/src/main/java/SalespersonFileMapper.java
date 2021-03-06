import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SalespersonFileMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

  private static final String fileTag = "UD~";

  private static final String DATA_SEPARATOR = ",";

  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

    String values[] = value.toString().split(DATA_SEPARATOR);

    StringBuilder dataStringBuilder = new StringBuilder();

    for (int index = 0; index < values.length; index++) {

      if (index != 0) {

        dataStringBuilder.append(values[index].toString().trim() + DATA_SEPARATOR);

      } else {

        dataStringBuilder.append(fileTag);

      }

    }

    String dataString = dataStringBuilder.toString();

    if (dataString != null && dataString.length() > 1) {

      dataString = dataString.substring(0, dataString.length() - 1);

    }

    dataStringBuilder = null;

    context.write(new LongWritable(Long.parseLong(values[0])), new Text(dataString));

  }

}
