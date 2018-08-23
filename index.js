function ProtoJson(protocal) {
  this.enmap = this.genKey2NumMap(protocal);
  this.demap = this.genNum2KeyMap(protocal);
}

ProtoJson.prototype = {
  genNum2KeyMap: function(protocal) {
    var re = {};
    var sortKeys = Object.keys(protocal).sort();
    sortKeys.unshift('');
    var len = sortKeys.length;
    for (var i = 1; i < len; i++) {
      var key = sortKeys[i];
      var val = protocal[key];
      if (!val) {
        re[i] = key;
      } else if (type(val) === '[object Object]') {
        var tmp = {};
        tmp[key] = this.genNum2KeyMap(val);
        re[i] = tmp;
      } else if (type(val) === '[object Array]') {
        var tmp = {};
        tmp[key] = this.genNum2KeyMapArray(val);
        re[i] = tmp;
      }
    }
    return re;
  },
  genNum2KeyMapArray: function(protocalArray) {
    var self = this;
    var re = protocalArray.map(function(val, ind) {
      var reTmp;
      reTmp = self.genNum2KeyMap(val);
      return reTmp;
    });
    return re;
  },
  genKey2NumMap: function(protocal) {
    var re = {};
    var sortKeys = Object.keys(protocal).sort();
    sortKeys.unshift('');
    var len = sortKeys.length;
    for (var i = 1; i < len; i++) {
      var key = sortKeys[i];
      var val = protocal[key];
      if (!val) {
        re[key] = i;
      } else if (type(val) === '[object Object]') {
        var tmp = {};
        tmp[i] = this.genKey2NumMap(val);
        re[key] = tmp;
      } else if (type(val) === '[object Array]') {
        var tmp = {};
        tmp[i] = this.genKey2NumMapArray(val);
        re[key] = tmp;
      }
    }
    return re;
  },
  genKey2NumMapArray: function(protocalArray) {
    var self = this;
    var re = protocalArray.map(function(val, ind) {
      var reTmp;
      reTmp = self.genKey2NumMap(val);
      return reTmp;
    });
    return re;
  },
  _encode: function(map, data) {
    if (type(data) !== '[object Object]') {
      return data;
    }
    var re = {};
    for (var key in data) {
      var val = data[key];
      var num = map[key];
      if (!num) {
        re[key] = val;
      } else if (type(num) === '[object Object]') {
        for (var n in num) {
          var subMap = num[n];
          if (type(subMap) === '[object Array]') {
            re[n] = this._encodeArray(subMap, val);
          } else {
            re[n] = this._encode(subMap, val);
          }
        }
      } else {
        re[num] = val;
      }
    }
    return re;
  },

  _encodeArray: function(mapArray, data) {
    var self = this;
    if (type(data) !== '[object Array]') {
      return data;
    }
    var re = [];
    var protocal = mapArray[0];
    re = data.map(function(item) {
      var reData = self._encode(protocal, item);
      return reData;
    });
    return re;
  },
  encode: function(data) {
    return this._encode(this.enmap, data);
  },
  decode: function(data) {
    return this._encode(this.demap, data);
  }
};

function type(p) {
  return Object.prototype.toString.call(p);
}

module.exports = ProtoJson;
