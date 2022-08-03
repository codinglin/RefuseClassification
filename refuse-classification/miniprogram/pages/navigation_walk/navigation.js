var amapFile = require('../../utils/amap-wx.js');
var config = require('../../utils/config.js');

Page({
  data: {
    markers: [],
    distance: '',
    cost: '',
    polyline: []
  },
  onLoad: function (e) {
    let { markerId, latitude, longitude, latitude2, longitude2, city, name, desc } = e;
    let markers = [
      {
        iconPath: "../../images/mapicon_navi_s.png",
        id: 0,
        latitude: latitude,
        longitude: longitude,
        width: 23,
        height: 33
      }, {
        iconPath: "../../images/mapicon_navi_e.png",
        id: markerId,
        latitude: latitude2,
        longitude: longitude2,
        width: 24,
        height: 34
      }
    ]
    this.setData({
      markerId, latitude, longitude, latitude2, longitude2, markers, city, name, desc
    });
    var that = this;
    var key = config.Config.key;
    var myAmapFun = new amapFile.AMapWX({ key: key });
    myAmapFun.getWalkingRoute({
      origin: that.data.longitude + ',' + that.data.latitude,
      destination: that.data.longitude2 + ',' + that.data.latitude2,
      success: function (data) {
        var points = [];
        if (data.paths && data.paths[0] && data.paths[0].steps) {
          var steps = data.paths[0].steps;
          for (var i = 0; i < steps.length; i++) {
            var poLen = steps[i].polyline.split(';');
            for (var j = 0; j < poLen.length; j++) {
              points.push({
                longitude: parseFloat(poLen[j].split(',')[0]),
                latitude: parseFloat(poLen[j].split(',')[1])
              })
            }
          }
        }
        that.setData({
          polyline: [{
            points: points,
            color: "#0091ff",
            width: 6
          }]
        });
        if (data.paths[0] && data.paths[0].distance) {
          that.setData({
            distance: data.paths[0].distance + '米'
          });
        }
        if (data.paths[0] && data.paths[0].duration) {
          that.setData({
            cost: parseInt(data.paths[0].duration / 60) + '分钟'
          });
        }

      },
      fail: function (info) {

      }
    })
  },
  goDetail: function () {
    let { latitude, longitude, latitude2, longitude2 } = this.data;
    let url = `/pages/navigation_walk_detail/navigation?longitude=${longitude}&latitude=${latitude}&longitude2=${longitude2}&latitude2=${latitude2}`;
    wx.navigateTo({ url })
  },
  nav: function () {
    let { latitude2, longitude2, name, desc } = this.data;
    wx.openLocation({
      latitude: +latitude2,
      longitude: +longitude2,
      name,
      address: desc
    });
  }
})