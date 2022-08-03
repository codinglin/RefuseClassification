//index.js
//获取应用实例
const app = getApp()
var uploadImage = require('../../utils/uploadFile.js');
var util = require('../../utils/util.js');

Page({
   data: {
      //是否隐藏扫描物品的弹窗
      isHiddenScanModal: true,
      isHiddenInfoModal: true,
      itemDetail: ''
   },

   onLoad: function (options) {
      this.setData({
        kindList: getApp().globalSortData.kindList,
      });
    },

   //选择照片
   choose: function () {
      console.log("选择照片！");
      let self = this;
      wx.chooseImage({
         count: 9, // 默认最多一次选择9张图
         sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
         sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
         success: function (res) {
            // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
            var tempFilePaths = res.tempFilePaths;
            var nowTime = util.formatTime(new Date());

            //支持多图上传
            for (var i = 0; i < res.tempFilePaths.length; i++) {
               //显示消息提示框
               wx.showLoading({
                  title: '上传中' + (i + 1) + '/' + res.tempFilePaths.length,
                  mask: true
               })

               //上传图片
               //你的域名下的/cbb文件下的/当前年月日文件下的/图片.png
               //图片路径可自行修改
               uploadImage(res.tempFilePaths[i], 'lin/' + nowTime + '/',
                  function (result) {
                     console.log("======上传成功图片地址为：", result);
                     app.get("/recognition/getFileName", {
                        fileName: result
                     }).then(res => {
                        console.log(res)
                        self.setData({
                           itemDetail: res.item,
                           isHiddenScanModal: false
                        })
                     }).catch(err => {
                        console.log(err);
                     })
                     wx.hideLoading();
                  }, function (result) {
                     console.log("======上传失败======", result);
                     wx.hideLoading()
                  }
               )
            }
         }
      })
   },
   // modal_hidden: function () {
   //    this.setData({
   //       isHiddenScanModal: true
   //    })
   // },

   //点击扫描识别的项目
   doClickScanItem: function (event) {
      this.setData({
         isHiddenScanModal: true
      })
      let name = event.currentTarget.dataset.name
      console.log("【选择的物品】", name.substring(0, name.length - 6))

      this.setData({
         inputTxt: name.substring(0, name.length - 6)
      })

      //开始搜索
      this.doClick(null);
   },
   //点击搜索按钮
   doClick: function (event) {
      var that = this;
      //判断输入的内容是否有效
      if (that.data.inputTxt == null) {
         wx.showToast({
            title: '请输入有效内容！！',
            icon: 'none',
            duration: 2000,
            mask: true
         })
         return;
      }

      //清空历史搜索项
      that.setData({
         searchItmes: []
      })
      console.log(that.data.inputTxt)
      that.searchFunction();
   },

   //搜索动作
   searchFunction: function () {
      console.log("【开始搜索】", this.data.inputTxt)
      let memberId = wx.getStorageSync('memberId');
      app.get("/item/getItemSort", {
         name: this.data.inputTxt
      }).then(res => {
         console.log(res.sortItem);
         // console.log(memberId)
         if (memberId) {
            console.log("[memberId]" + memberId);
            console.log(res.sortItem[0].id)
            app.post("/search-record/addRecord", {
               itemId: res.sortItem[0].id,
               userId: memberId,
            }, this).then(res => {
               wx.setStorageSync('memberId', memberId);
            }).catch(err => {
               console.log(err);
            })
         }

         this.setData({
            searchItmes: res.sortItem
         })
         if (res.sortItem.length == 0) {
            wx.showToast({
               title: '搜索的可能是外星物品哦！！',
               icon: 'none',
               duration: 2000,
               mask: true
            })
         }
      }).catch(err => {
         console.log(err);
      })
   },

   //选择项目
   doClickItem: function (event) {
      console.log("【选择的项目】", event)
      var _type = event.currentTarget.dataset.type;
      var _name = event.currentTarget.dataset.name;
      var _id = event.currentTarget.id;

      console.log("【选择的ID】", _id)
      //console.log(this.data.kindList)
      for (var i = 0; i < this.data.kindList.length; i++) {
         if (this.data.kindList[i].text == _type) {
            //显示详细信息
            var itemInfo = {
               _txt: _name,
               _type: this.data.kindList[i]
            }

            this.setData({
               selectItem: itemInfo,
               isHiddenInfoModal: false
            })

            //console.log("【详细】", this.data.selectItem)
            return;
         }
      }
   },
   //点击关闭弹窗详细信息
   modal_hidden: function () {
      this.setData({
         isHiddenInfoModal: true,
         isHiddenScanModal: true
      })
   }
})
