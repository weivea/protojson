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

## java 端 的最简实践

命令查看测试结果
```
javac -cp ./:./protojsonjava/lib/gson.jar protojsonjava/TestJSON.java

java -cp ./:./protojsonjava/lib/gson.jar protojsonjava/TestJSON
```

