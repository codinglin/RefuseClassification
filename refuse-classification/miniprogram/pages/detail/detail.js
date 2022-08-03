// pages/detail/detail.js
var app = getApp();
var Auth = require('../../utils/auth.js');
let config = require("../../http/env.js")
let env="Dev"
var WxParse = require('../../wxParse/wxParse.js');
let isFocusing = false
Page({

  /**
   * 页面的初始数据
   */
  data: {
    config:config[env],
    title: '文章内容',
    detailDate: '',
    total_comments: '',
    pageviews: '',
    displayAudio: 'none',
    detailSummaryHeight: '',
    wxParseData: {},
    detail: {},
    commentsList: [],
    ChildrenCommentsList: [],
    commentCount: '',
    detailDate: '',
    commentValue: '',
    display: 'block',
    showerror: 'none',
    page: 1,
    isLastPage: false,
    parentID: "0",
    focus: false,
    placeholder: "评论...",
    scrollHeight: 0,
    postList: [],
    link: '',
    dialog: {
      title: '',
      content: '',
      hidden: true
    },
    content: '',
    isShow: false,//控制menubox是否显示
    isLoad: true,//解决menubox执行一次  
    menuBackgroup: false,
    likeImag: "like.png",
    likeList: [],
    likeCount: 0,
    displayLike: 'none',
    userid: "",
    toFromId: "",
    commentdate: "",
    flag: 1,
    enableComment: true,
    isLoading: false,
    total_comments: 0,

    isLoginPopup: false,

    openid: "",
    userInfo: {},
    system: '',

    isPlayAudio: false,
    audioSeek: 0,
    audioDuration: 0,
    showTime1: '00:00',
    showTime2: '00:00',
    audioTime: 0,
    displayAudio: 'none',
    shareImagePath: '',
    detailSummaryHeight: '',
    detailAdsuccess: true,
    detailTopAdsuccess: true,
    fristOpen: false,
    platform: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var self = this;
    Auth.setUserInfoData(self);
    Auth.checkLogin(self);
    console.log("文章ID:", options.article_Id);
    this.setData({
      article_Id: JSON.parse(options.article_Id)
    })
    //console.log(this.data.article_Id)
    let articleId = JSON.parse(options.article_Id);
    app.get("/sortingArticle/getArticleById", {
      Id: articleId
    }).then(res => {
      console.log(res);
      // 设置页面标题：文章分类
      // wx.setNavigationBarTitle({
      //   // title: res.data.title.rendered
      //   title: res.data.category_name
      // });

      this.setData({
        detailDate: res.article.gmtCreate,
        total_comments: res.article.commentCount,
        pageviews: res.article.pageviews,
        title: res.article.title
        //wxParseData:wxParseData
      })
      WxParse.wxParse('article', 'html', res.article.detail, self, 5);
      if (res.article.commentCount != null && res.article.commentCount != '') {
        self.setData({
          commentCount: "有" + res.article.commentCount + "条评论"
        });
      };
      //console.log(res.article.commentVOS);
      self.fristOpenComment(res.article.commentVOS);
    }).catch(err => {
      console.log(err);
    })
  },

  agreeGetUser: function (e) {
    let self = this;
    Auth.checkAgreeGetUser(e, app, self, '0');

  },

  // 首次加载评论
  fristOpenComment(commentVOS) {
    this.setData({
      commentsList: []
    })
    this.fetchCommentData(commentVOS);
  },

  //获取评论
  fetchCommentData: function (commentVOS) {
    var self = this;
    self.setData({
      commentsList: [].concat(self.data.commentsList, commentVOS)
    });
  },
  //显示或隐藏功能菜单
  ShowHideMenu: function () {
    this.setData({
      isShow: !this.data.isShow,
      isLoad: false,
      menuBackgroup: !this.data.false
    })
  },
  //点击非评论区隐藏功能菜单
  hiddenMenubox: function () {
    this.setData({
      isShow: false,
      menuBackgroup: false
    })
  },

  formSubmit: function (e) {
    var self = this;
    var comment = e.detail.value.inputComment;
    if (comment.length === 0) {
      self.setData({
        'dialog.hidden': false,
        'dialog.title': '提示',
        'dialog.content': '没有填写评论内容。'

      });
    }
    else {
      wx.request({
        url: this.data.config.baseApi+'/user/getMemberInfo',
        header: {
          'content-type': 'application/json',
          'token': wx.getStorageSync('token')
        },
        success(res) {
          //console.log(res.data.data.memberId);
          //console.log(self.data.article_Id);
          let memberId=res.data.data.memberId;
          app.post("/article-comment/addComment", {
            articleId: self.data.article_Id,
            content: comment,
            userId: memberId,
            parentId: self.data.parentID
          }, e).then(res => {
            //console.log(res);
            //console.log(self.data.parentID);
            wx.setStorageSync('memberId',memberId);
            self.setData({
              content: '',
              parentID: "0",
              userid: 0,
              placeholder: "评论...",
              focus: false,
              commentsList: []
            });
            var commentCounts = parseInt(self.data.total_comments) + 1;
            self.setData({
              total_comments: commentCounts,
              commentCount: "有" + commentCounts + "条评论"
            });
            self.fristOpenComment(res.commentVOS);  
          }).catch(err => {
            console.log(err);
          })
        }
      })
    }
  },

  goHome: function () {
    wx.switchTab({
      url: '../index/index'
    })
  },

  replay: function (e) {
    var self = this;
    var id = e.target.dataset.id;
    var name = e.target.dataset.name;
    //var userid = e.target.dataset.userid;
    isFocusing = true;
    if (self.data.enableComment == "1") {
      self.setData({
        parentID: id,
        placeholder: "回复" + name + ":",
        focus: true  
      });

    }
    // console.log('toFromId', toFromId);
    // console.log('replay', isFocusing);
  },
  onReplyBlur: function (e) {
    var self = this;
    // console.log('onReplyBlur', isFocusing);
    if (!isFocusing) {
      {
        const text = e.detail.value.trim();
        if (text === '') {
          self.setData({
            parentID: "0",
            placeholder: "评论..."
            //userid: ""         
          });
        }

      }
    }
    // console.log(isFocusing);
  },
  onRepleyFocus: function (e) {
    var self = this;
    isFocusing = false;
    if (!self.data.focus) {
      self.setData({ focus: true })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    var self = this;
    Auth.checkSession(self, 'isLoginNow');
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  closeLoginPopup() {
    this.setData({ isLoginPopup: false });
  },
  openLoginPopup() {
    this.setData({ isLoginPopup: true });
  },
  confirm: function () {
    this.setData({
      'dialog.hidden': true,
      'dialog.title': '',
      'dialog.content': ''
    })
  }
})