var amapFile = require('../../utils/amap-wx.js');
var config = require('../../utils/config.js');
var app = getApp();


var markersData = [];
Page({
  data: {
    markers: [],
    latitude: '',
    longitude: '',
    textData: {},
    city: '杭州'
  },
  makertap: function (e) {
    var id = e.markerId;
    var that = this;
    that.showMarkerInfo(markersData, id);
    that.changeMarkerColor(markersData, id);
  },
  onLoad: function (e) {
    var that = this;
    var key = config.Config.key;
    var myAmapFun = new amapFile.AMapWX({ key: key });
    app.get("/sorting-position/getAllPositions", {})
      .then(res => {
        // this.setData({
        //   markers: res.markers
        // });
        markersData = res.markers;
        var markers_new = [];
        markersData.forEach(function (item, index) {
          markers_new.push({
            id: item.id,
            latitude: item.latitude,
            longitude: item.longitude,
            iconPath: item.iconPath,
            width: item.width,
            height: item.height
          })
        })
        if (markersData.length > 0) {
          that.setData({
            markers: markers_new
          });
          // that.setData({
          //   city: poisData[0].cityname || ''
          // });
          that.setData({
            latitude: markersData[0].latitude
          });
          that.setData({
            longitude: markersData[0].longitude
          });
          that.showMarkerInfo(markersData, 0);
        } else {
          wx.getLocation({
            type: 'gcj02',
            success: function (res) {
              that.setData({
                latitude: res.latitude
              });
              that.setData({
                longitude: res.longitude
              });
              that.setData({
                city: '北京市'
              });
            },
            fail: function () {
              that.setData({
                latitude: 39.909729
              });
              that.setData({
                longitude: 116.398419
              });
              that.setData({
                city: '北京市'
              });
            }
          })

          that.setData({
            textData: {
              name: '抱歉，未找到结果',
              desc: ''
            }
          });
        }
      }).catch(err => {
        console.log(err);
      })
  },
  bindInput: function (e) {
    var that = this;
    var url = '../inputtips/input';
    if (e.target.dataset.latitude && e.target.dataset.longitude && e.target.dataset.city) {
      var dataset = e.target.dataset;
      url = url + '?lonlat=' + dataset.longitude + ',' + dataset.latitude + '&city=' + dataset.city;
    }
    wx.redirectTo({
      url: url
    })
  },
  showMarkerInfo: function (data, i) {
    var that = this;
    console.log(data)
    that.setData({
      textData: {
        name: data[i].name,
        desc: data[i].address
      }
    });
  },
  changeMarkerColor: function (data, i) {
    var that = this;
    var markers = [];
    for (var j = 0; j < data.length; j++) {
      if (j == i) {
        data[j].iconPath = "../../images/marker_checked.png";
      } else {
        data[j].iconPath = "../../images/marker.png";
      }
      markers.push({
        id: data[j].id,
        latitude: data[j].latitude,
        longitude: data[j].longitude,
        iconPath: data[j].iconPath,
        width: data[j].width,
        height: data[j].height
      })
    }
    that.setData({
      markers: markers,
      markerId: i
    });
  },

  getRoute: function() {
    // 起点
    let { latitude, longitude, markers, markerId, city, textData } = this.data;
    let { name, desc } = textData;
    if (!markers.length) return;
    // 终点
    let { latitude: latitude2, longitude: longitude2 } = markers[markerId];
    let url = `/pages/navigation_walk/navigation?markerId=${markerId}&longitude=${longitude}&latitude=${latitude}&longitude2=${longitude2}&latitude2=${latitude2}&city=${city}&name=${name}&desc=${desc}`;
    console.log(url)
    wx.navigateTo({ url });
  },
  getRetrieve: function() {
    // 起点
    let { latitude, longitude, markers, markerId, city, textData } = this.data;
    let { name, desc } = textData;
    if (!markers.length) return;
    
    let url = `/pages/retrieveOrder/retrieveOrder?markerId=${markerId}&name=${name}&desc=${desc}`;
    console.log(markerId);
    wx.navigateTo({ url });
  }
})