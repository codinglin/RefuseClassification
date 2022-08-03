var area = require('../../utils/area.js')
var app = getApp();
let config = require("../../http/env.js")
let env = "Dev"
var p = 0, c = 0, d = 0
Page({
  data: {
    config: config[env],
    provinceName: [],
    provinceCode: [],
    provinceSelIndex: '',
    cityName: [],
    cityCode: [],
    citySelIndex: '',
    districtName: [],
    districtCode: [],
    districtSelIndex: '',
    showMessage: false,
    messageContent: '',
    showDistpicker: false,
    name: '',
    tel: '',
  },
  onLoad: function (options) {
    // 载入时要显示再隐藏一下才能显示数据，如果有解决方法可以在issue提一下，不胜感激:-)
    // 初始化数据
    this.setAreaData()
    // console.log(options)
    if (options) {
      let address = JSON.parse(options.id);
      console.log(address)
      this.setData({
        id: address.id,
        name: address.name,
        tel: address.tel,
        address: address.address,
        pro: address.province,
        cit: address.city,
        dist: address.district
      })
    }
  },
  setAreaData: function (p, c, d) {
    var p = p || 0 // provinceSelIndex
    var c = c || 0 // citySelIndex
    var d = d || 0 // districtSelIndex
    // 设置省的数据
    var province = area['100000']
    var provinceName = [];
    var provinceCode = [];
    for (var item in province) {
      provinceName.push(province[item])
      provinceCode.push(item)
    }
    this.setData({
      provinceName: provinceName,
      provinceCode: provinceCode
    })
    // 设置市的数据
    var city = area[provinceCode[p]]
    var cityName = [];
    var cityCode = [];
    for (var item in city) {
      cityName.push(city[item])
      cityCode.push(item)
    }
    this.setData({
      cityName: cityName,
      cityCode: cityCode
    })
    // 设置区的数据
    var district = area[cityCode[c]]
    var districtName = [];
    var districtCode = [];
    for (var item in district) {
      districtName.push(district[item])
      districtCode.push(item)
    }
    this.setData({
      districtName: districtName,
      districtCode: districtCode
    })
  },
  changeArea: function (e) {
    p = e.detail.value[0]
    c = e.detail.value[1]
    d = e.detail.value[2]
    this.setAreaData(p, c, d)
  },
  showDistpicker: function () {
    this.setData({
      showDistpicker: true
    })
  },
  distpickerCancel: function () {
    this.setData({
      showDistpicker: false
    })
  },
  distpickerSure: function () {
    this.setData({
      provinceSelIndex: p,
      citySelIndex: c,
      districtSelIndex: d
    })
    this.distpickerCancel()
  },
  savePersonInfo: function (e) {
    var data = e.detail.value
    var telRule = /^1[3|4|5|7|8]\d{9}$/, nameRule = /^[\u2E80-\u9FFF]+$/
    if (data.name == '') {
      this.showMessage('请输入姓名')
    } else if (!nameRule.test(data.name)) {
      this.showMessage('请输入中文名')
    } else if (data.tel == '') {
      this.showMessage('请输入手机号码')
    } else if (!telRule.test(data.tel)) {
      this.showMessage('手机号码格式不正确')
    } else if (data.province == '') {
      this.showMessage('请选择所在地区')
    } else if (data.city == '') {
      this.showMessage('请选择所在地区')
    } else if (data.district == '') {
      this.showMessage('请选择所在地区')
    } else if (data.address == '') {
      this.showMessage('请输入详细地址')
    } else {
      let memberId = wx.getStorageSync('memberId');
      console.log(this.data.id)
      if (!this.data.id) {
        app.post("/userAddress/addAddress", {
          userId: memberId,
          tel: data.tel,
          province: data.province,
          city: data.city,
          district: data.district,
          address: data.address,
          name: data.name,
          isDefault: data.default ? 1 : 0
        }, e).then(res => {
          this.showMessage(' 保存成功')
          wx.navigateBack({
            delta: 1,
          })
        }).catch(err => {
          console.log(err);
        })
      } else {
        app.put("/userAddress/updateAddress", {
          id: this.data.id,
          userId: memberId,
          tel: data.tel,
          province: data.province,
          city: data.city,
          district: data.district,
          address: data.address,
          name: data.name,
          isDefault: data.default ? 1 : 0
        }, e).then(res => {
          this.showMessage(' 修改成功')
          wx.navigateBack({
            delta: 1,
          })
        }).catch(err => {
          console.log(err);
        })
      }
    }

  },
  showMessage: function (text) {
    var that = this
    that.setData({
      showMessage: true,
      messageContent: text
    })
    setTimeout(function () {
      that.setData({
        showMessage: false,
        messageContent: ''
      })
    }, 3000)
  }
})