# proto-json

一个基于约定协议的json压缩，解压缩 插件，

## install

`npm install @weivea/proto-json`

## usage

运行测试
```
npm run test
```


```javascript
var ProtoJson = require('@weivea/proto-json');
var protocal = {
  width: '',
  height: '',
  weight: '',
  age:'',
  name: '',
  partner: {
      width: '',
      height: '',
      weight: '',
      age:'',
      name: ''
  },
  children: [
      {
          width: '',
          height: '',
          weight: '',
          age:'',
          name:'',
          friends: [
              {
                  name:'',
                  type:'',
                  gender:''
              }
          ]
      }
  ]
}


var data = {
  width: 50,
  height: 180,
  weight: 50,
  age:30,
  name: 'lalala',
  partner: {
      width: 28,
      height: 170,
      weight: 49,
      age:30,
      name: 'bobo',
      adc:'acasdv'
  },
  children: [
      {
          width: 30,
          height: 100,
          weight: 40,
          age:12,
          name:'liliya',
          friends: [
              {
                  name:'tom',
                  type:'cat'
              },
              {
                  name:'bam',
                  type:'dog'
              }
          ]
      },
      {
          width: 45,
          height: 160,
          weight: 45,
          age:15,
          name:'tifny',
          friends: [
              {
                  name:'erix',
                  gender:'male'
              }
          ]
      },
  ]
}
var dataStr = JSON.stringify(data);
console.log('原数据:', dataStr);
console.log('');

var protoJsoner = new ProtoJson(protocal);

var encodejson = protoJsoner.encode(data);
console.log('编码数据:', JSON.stringify(encodejson));
console.log('');

var decodejson = protoJsoner.decode(encodejson);
console.log('解码数据:', JSON.stringify(decodejson));

```

打印输出：

```
原数据: {"width":50,"height":180,"weight":50,"age":30,"name":"lalala","partner":{"width":28,"height":170,"weight":49,"age":30,"name":"bobo","adc":"acasdv"},"children":[{"width":30,"height":100,"weight":40,"age":12,"name":"liliya","friends":[{"name":"tom","type":"cat"},{"name":"bam","type":"dog"}]},{"width":45,"height":160,"weight":45,"age":15,"name":"tifny","friends":[{"name":"erix","gender":"male"}]}]}

编码数据: {"1":30,"2":[{"1":12,"2":[{"2":"tom","3":"cat"},{"2":"bam","3":"dog"}],"3":100,"4":"liliya","5":40,"6":30},{"1":15,"2":[{"1":"male","2":"erix"}],"3":160,"4":"tifny","5":45,"6":45}],"3":180,"4":"lalala","5":{"1":30,"2":170,"3":"bobo","4":49,"5":28,"adc":"acasdv"},"6":50,"7":50}

解码数据: {"age":30,"children":[{"age":12,"friends":[{"name":"tom","type":"cat"},{"name":"bam","type":"dog"}],"height":100,"name":"liliya","weight":40,"width":30},{"age":15,"friends":[{"gender":"male","name":"erix"}],"height":160,"name":"tifny","weight":45,"width":45}],"height":180,"name":"lalala","partner":{"age":30,"height":170,"name":"bobo","weight":49,"width":28,"adc":"acasdv"},"weight":50,"width":50}

```


## java 端 的最简实践

命令查看测试结果
```
javac -cp ./:./protojsonjava/lib/gson.jar protojsonjava/TestJSON.java

java -cp ./:./protojsonjava/lib/gson.jar protojsonjava/TestJSON
```

