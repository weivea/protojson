package protojsonjava;
import java.io.*;
import java.util.Map;



public class TestJSON {
  @SuppressWarnings("unchecked")
  public static void main (String args[]) throws IOException {
    // String protocal = "{\"width\":\"\",\"height\":\"\",\"weight\":\"\",\"age\":\"\",\"name\":\"\",\"partner\":{\"width\":\"\",\"height\":\"\",\"weight\":\"\",\"age\":\"\",\"name\":\"\"},\"children\":[{\"width\":\"\",\"height\":\"\",\"weight\":\"\",\"age\":\"\",\"name\":\"\",\"friends\":[{\"name\":\"\",\"type\":\"\",\"gender\":\"\"}]}]}";
    // String jsonString = "{\"width\":50,\"height\":180,\"weight\":50,\"age\":30,\"name\":\"lalala\",\"partner\":{\"width\":28,\"height\":170,\"weight\":49,\"age\":30,\"name\":\"bobo\",\"adc\":\"acasdv\"},\"children\":[{\"width\":30,\"height\":100,\"weight\":40,\"age\":12,\"name\":\"liliya\",\"friends\":[{\"name\":\"tom\",\"type\":\"cat\"},{\"name\":\"bam\",\"type\":\"dog\"}]},{\"width\":45,\"height\":160,\"weight\":45,\"age\":15,\"name\":\"tifny\",\"friends\":[{\"name\":\"erix\",\"gender\":\"male\"}]}]}";
    
    FileInputStream fip = new FileInputStream("/Users/weijianli/work/learnJava/protojson/protocal.json");
    InputStreamReader reader = new InputStreamReader(fip, "UTF-8");
    StringBuffer sb = new StringBuffer();
    while (reader.ready()) {
        sb.append((char) reader.read());
        // 转成char加到StringBuffer对象中
    }
    reader.close();
    // 关闭读取流
    fip.close();
    String protocal = sb.toString();
    System.out.println("protocal---------------------------------");
    System.out.println(protocal);



    ProtoJSON protoJson = new ProtoJSON(protocal);
    SampleDemo sd = new SampleDemo();

    Map<String, Object> sdMap = protoJson.toMap(sd, SampleDemo.class);
    String json = protoJson.toJson(sd);
    System.out.println("正常转换JSON---------------------------------");
    System.out.println(json);
    String protoJsonStr = protoJson.toProtoJson(sd);
    System.out.println("protoJson---------------------------------");
    System.out.println(protoJsonStr);

    String decodeJsonStr =  protoJson.decodeProtoJson(protoJsonStr);
    System.out.println("decodeJsonStr-----------------------------");
    System.out.println(decodeJsonStr);
    SampleDemo resd = protoJson.fromProtoJson(protoJsonStr, SampleDemo.class);
    System.out.println("decodeObject-----------------------------");

    System.out.println(resd.name);
    System.out.println(resd.age);
    System.out.println(resd.son.age);
  }
}
