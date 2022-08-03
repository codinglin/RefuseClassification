var amapFile = require('../../utils/amap-wx.js');
var config = require('../../utils/config.js');

Page({
  data: {
    steps: {}
  },
  onLoad: function(e) {
    let {latitude, longitude, latitude2, longitude2} = e;
    this.setData({
      latitude, longitude, latitude2, longitude2
    });
    var that = this;
    var key = config.Config.key;
    var myAmapFun = new amapFile.AMapWX({key: key});
    myAmapFun.getWalkingRoute({
      origin: that.data.longitude + ',' + that.data.latitude,
      destination: that.data.longitude2 + ',' + that.data.latitude2,
      success: function(data){
        if(data.paths && data.paths[0] && data.paths[0].steps){
          that.setData({
            steps: data.paths[0].steps
          });
        }
          
      },
      fail: function(info){

      }
    })
  }
})