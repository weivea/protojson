package protojsonjava;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Collections;
import java.util.Arrays;
import java.util.Objects;

import com.google.gson.*;


public class ProtoJSON {
  public String ptotocalJson;
  private Map<String, Object> num2KeyMap;
  private Map<String, Object> key2NumMap;
  public static Gson gson = new Gson();
  public ProtoJSON(String ptotocalJson) {
    this.ptotocalJson = ptotocalJson;
    Map<String, Object> out = toMap(ptotocalJson);


    key2NumMap = genKey2NumMap(out);


    num2KeyMap = genNum2KeyMap(out);

    
    // genNum2KeyMap(out);
  }

  /**
   * 生成<numbre, key> 的map
   */
  @SuppressWarnings("unchecked")
  public static Map<String, Object> genNum2KeyMap(Map<String, Object> protocal) {
    Map<String, Object> re = new HashMap<String, Object>();
    Set<String> keySet = protocal.keySet();
    ArrayList<String> keyList =  new ArrayList<String>(keySet);
    int i = 0;

    Collections.sort(keyList);
    
    for(String key:keyList){
      i++;
      Object val = protocal.get(key);
      if (val instanceof JsonPrimitive) {    
        re.put(""+i, key);
      } else if (val instanceof Map) {
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put(key, genNum2KeyMap((Map<String, Object>) val));
        re.put(""+i, subMap);
      } else if (val instanceof ArrayList) {
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put(key, genNum2KeyMapArray((ArrayList<Map<String, Object>>) val));
        re.put(""+i, subMap);
      }
    }
    return re;
  }

  public static ArrayList<Map<String, Object>> genNum2KeyMapArray(ArrayList<Map<String, Object>> protocalList) {
    ArrayList<Map<String, Object>> re = new ArrayList<Map<String, Object>>();
    for(Map<String, Object> protocal:protocalList){
      re.add(genNum2KeyMap(protocal));
    }
    return re;
  }

  @SuppressWarnings("unchecked")
  public static Map<String, Object> genKey2NumMap(Map<String, Object> protocal) {
    Map<String, Object> re = new HashMap<String, Object>();
    Set<String> keySet = protocal.keySet();
    ArrayList<String> keyList =  new ArrayList<String>(keySet);
    int i = 0;

    Collections.sort(keyList);
    
    for(String key:keyList){
      ++i;
      Object val = protocal.get(key);
      if (val instanceof JsonPrimitive) {    
        re.put(key, i);
      } else if (val instanceof Map) {
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put(""+i, genKey2NumMap((Map<String, Object>) val));
        re.put(key, subMap);
      } else if (val instanceof ArrayList) {
        Map<String, Object> subMap = new HashMap<String, Object>();
        subMap.put(""+i, genKey2NumMapArray((ArrayList<Map<String, Object>>) val));
        re.put(key, subMap);
      }
    }
    return re;
  }
  public static ArrayList<Map<String, Object>> genKey2NumMapArray(ArrayList<Map<String, Object>> protocalList) {
    ArrayList<Map<String, Object>> re = new ArrayList<Map<String, Object>>();
    for(Map<String, Object> protocal:protocalList){
      re.add(genKey2NumMap(protocal));
    }
    return re;
  }

  @SuppressWarnings("unchecked")
  public Map<String, Object> changeKeyWhitMap(Map<String, Object> _key2NumMap, Map<String, Object> rawMap) {
    Map<String, Object> re = new HashMap<String, Object>();
    for (String key:rawMap.keySet()) {
      
      Object value = rawMap.get(key);
      Object num = _key2NumMap.get(key);

      if (num instanceof Map) {
        Map<String, Object> numMap = (Map<String, Object>) num;
        for (String subNum:numMap.keySet()) {
          Object subKey2NumMap = numMap.get(subNum);
          if (value instanceof ArrayList && subKey2NumMap instanceof ArrayList) {
            re.put(subNum, changeKeyWhitMapArray((ArrayList<Map<String, Object>>) subKey2NumMap, (ArrayList<Map<String, Object>>) value));
          } else if (value instanceof Map && subKey2NumMap instanceof Map) {
            re.put(subNum, changeKeyWhitMap((Map<String, Object>) subKey2NumMap, (Map<String, Object>) value));
          } else {
            re.put(key, value);
          }
        }
      } else if (Objects.isNull(num)) {
        re.put(key, value);
      } else {
        re.put(""+num, value);
      }
    }
    
    return re;
  }


  public ArrayList<Map<String, Object>> changeKeyWhitMapArray(ArrayList<Map<String, Object>> subKey2NumMapList, ArrayList<Map<String, Object>> valueList) {
    ArrayList<Map<String, Object>> re = new ArrayList<Map<String, Object>>();
    Map<String, Object> _key2NumMap = subKey2NumMapList.get(0);
    for(Map<String, Object> value:valueList){
      re.add(changeKeyWhitMap(_key2NumMap, value));
    }
    return re;
  }
  
  @SuppressWarnings("unchecked")
  public Map<String, Object> encodeMap (Map<String, Object> rawMap) {
    return changeKeyWhitMap(key2NumMap, rawMap);
  }
  @SuppressWarnings("unchecked")
  public Map<String, Object> decodeMap (Map<String, Object> rawMap) {

    Map<String, Object> re =  changeKeyWhitMap(num2KeyMap, rawMap);
    return re;
  }

  /**
   * 依据java对象返回JsonStr
   * 
   * @param src
   * @param typeOfSrc
   * @return
   */
  public static String toJson(java.lang.Object src, java.lang.reflect.Type typeOfSrc) {
    return gson.toJson(src, typeOfSrc);
  }

  /**
   * 依据java对象返回JsonStr
   * 
   * @param src
   * @return
   */
  public static String toJson(java.lang.Object src) {
    return gson.toJson(src);
  }

  /**
   * 依据java对象返回JsonStr
   * 
   * @param src
   * @return
   */
  @SuppressWarnings("unchecked")
  public String toProtoJson(java.lang.Object src) {
    Map<String, Object> rawMap = toMap(src);
    Map<String, Object> map3 = encodeMap(rawMap);
    return ProtoJSON.gson.toJson(map3);
  }
  /**
   * 依据java对象返回JsonStr
   * 
   * @param src
   * @param typeOfSrc
   * @return
   */
  @SuppressWarnings("unchecked")
  public String toProtoJson(java.lang.Object src, java.lang.reflect.Type typeOfSrc) {
    Map<String, Object> rawMap = toMap(src, typeOfSrc);
    Map<String, Object> map3 = encodeMap(rawMap);
    return ProtoJSON.gson.toJson(map3);
  }

  /**
   * 依据java对象返回JsonStr
   * 
   * @param json
   * @param typeOfT
   * @return
   */
  public static <T> T fromJson(java.lang.String json, java.lang.reflect.Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }


  /**
   * 依据java对象返回JsonStr
   * 
   * @param src
   * @param typeOfSrc
   * @return
   */
  public String decodeProtoJson(java.lang.String json) {
    Map<String, Object> rawMap = toMap(json);
    Map<String, Object> map3 = decodeMap(rawMap);
    String resultJson = ProtoJSON.gson.toJson(map3);
    return resultJson;
  }

  /**
   * 依据java对象返回JsonStr
   * 
   * @param json
   * @param typeOfT
   * @return
   */
  public <T> T fromProtoJson(java.lang.String json, java.lang.reflect.Type typeOfT) {
    String resultJson = decodeProtoJson(json);
    return ProtoJSON.fromJson(resultJson, typeOfT);
  }

  /**
   * 获取JsonObjecb
   * 
   * @param json
   * @return
   */
  public static JsonObject parseJson(String json) {
    JsonParser parser = new JsonParser();
    JsonObject jsonObj = parser.parse(json).getAsJsonObject();
    return jsonObj;
  }

  /**
   * 依据json字符串返回Map对象
   * 
   * @param json
   * @return
   */
  public static Map<String, Object> toMap(String json) {
    return ProtoJSON.toMap(ProtoJSON.parseJson(json));
  }


  /**
   * 依据java对象返回Map对象
   * 
   * @param src
   * @param typeOfSrc
   * @return
   */
  public static Map<String, Object> toMap(java.lang.Object src, java.lang.reflect.Type typeOfSrc) {
    JsonElement jsonTree = gson.toJsonTree(src, typeOfSrc);
    return ProtoJSON.toMap((JsonObject) jsonTree);
  }

  /**
   * 依据java对象返回Map对象
   * 
   * @param src
   * @return
   */
  public static Map<String, Object> toMap(java.lang.Object src) {
    JsonElement jsonTree = gson.toJsonTree(src);
    return ProtoJSON.toMap((JsonObject) jsonTree);
  }
  /**
   * 将JSONObjec对象转换成Map-List集合
   * 
   * @param json
   * @return
   */
  public static Map<String, Object> toMap(JsonObject json) {
    Map<String, Object> map = new HashMap<String, Object>();
    Set<Entry<String, JsonElement>> entrySet = json.entrySet();
    for (Iterator<Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext();) {
      Entry<String, JsonElement> entry = iter.next();
      String key = entry.getKey();
      Object value = entry.getValue();
      if (value instanceof JsonArray)
        map.put((String) key, toList((JsonArray) value));
      else if (value instanceof JsonObject)
        map.put((String) key, toMap((JsonObject) value));
      else
        map.put((String) key, value);
    }
    return map;
  }

  /**
   * 将JSONArray对象转换成List集合
   * 
   * @param json
   * @return
   */
  public static List<Object> toList(JsonArray json) {
    List<Object> list = new ArrayList<Object>();
    for (int i = 0; i < json.size(); i++) {
      Object value = json.get(i);
      if (value instanceof JsonArray) {
        list.add(toList((JsonArray) value));
      } else if (value instanceof JsonObject) {
        list.add(toMap((JsonObject) value));
      } else {
        list.add(value);
      }
    }
    return list;
  }
}


