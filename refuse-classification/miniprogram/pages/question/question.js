// pages/sequence/sequence.js
var app = getApp();
var qs = require('../../utils/res.js')
var QC = new require('../../utils/question_control.js')
var questioncontrol = QC.questionControl

Page({

  /**
   * 页面的初始数据
   */
  data: {
    questions: {}
  },

  favorite_list: new Set(),
  wrong_list: new Set(),
  view_list: [],
  vid: 0,

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    const self = this
    const t = options.type
    this.setData({ learning_type: t })
    qs.getQuestion().then(res => {
      // let questions = this.loadQuestions().questions
      let view_list = wx.getStorageSync(t + 'list')
      let favorite_list = wx.getStorageSync('favorite_list')
      if (favorite_list) {
        favorite_list = favorite_list.split(',').map(x => parseInt(x))
        self.setFavoriteList(favorite_list)
      }
      self.setData({ questions: res.questions })
      if (t == 'favorite') {
        self.view_list = favorite_list
        self.vid = -1
        self.nextQuestion()
        return
      }
      let wrong_list = wx.getStorageSync('wrong_list')
      if (wrong_list) {
        wrong_list = wrong_list.split(',').map(x => parseInt(x))
        self.setWrongList(wrong_list)
      }

      let vid = wx.getStorageSync(t + 'vid')
      if (vid) {
        vid = parseInt(vid)
      } else {
        vid = 0
      }
      if (vid > 3) {
        view_list = view_list.split(',').map(x => parseInt(x))
        wx.showModal({
          title: '是否继续学习',
          content: '上次你学习到' + (vid + 1) + '个问题，是否继续？',
          success: function (res) {
            if (res.confirm) {
              self.vid = vid - 1
              self.view_list = view_list
              self.nextQuestion()
            }
            else {
              self.vid = -1
              self.view_list = self.generateList(t, self.getQuestionCount(res.questions))
              self.nextQuestion()
            }
          },
          fail: function () {

          }
        })
      } else {
        self.vid = -1
        self.view_list = self.generateList(t, self.getQuestionCount(res.questions))
        self.nextQuestion()
      }
    })
  },
  generateList: function (t, count) {
    var list = [];
    for (var i = 0; i < count; i++) {
      list.push(i);
    }
    if (t == 'random') {
      list = this.shuffle(list)
    }
    return list
  },

  shuffle: function (a) {
    for (let i = a.length - 1; i > 0; i--) {
      const j = Math.floor(Math.random() * (i + 1));
      [a[i], a[j]] = [a[j], a[i]];
    }
    return a;
  },
  nextQuestion: function () {
    if (this.finishedYet()) {
      wx.showModal({
        title: 'Congratulations!',
        content: '全部学完了',
      })
      return
    }
    //console.log(appPage.data.favorite_list);
    let question = this.getNextQuestion()
    //console.log(question);
    let favorite = this.isFavorite()
    this.setNewQuestion(question, favorite)
  },
  previousQuestion: function () {
    let question = this.getPreviousQuestion()
    let favorite = this.isFavorite()
    this.setNewQuestion(question, favorite)
  },
  setNewQuestion: function (question, favorite) {
    //console.log(question.answer)
    this.setData({
      question: question,
      answer: question.answer,
      favorite: favorite,
      correctid: '',
      wrongid: '',
      disable: '',
      pending: false
    })
  },
  selectAnswer: function (evt) {
    self = this
    let selected = evt.currentTarget.dataset.id
    let act = this.data.answer
    if (selected == act) {
      this.setData({
        correctid: selected,
        disable: 'disabled',
        pending: true
      })
      setTimeout(function () {
        self.nextQuestion()
      }, 1000)
    }
    else {
      this.setData({ wrongid: selected })
    }
  },
  addFavorite: function () {
    let isFavorite = this.toggleFavorite()
    this.setData({ favorite: isFavorite })

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    var animation = wx.createAnimation({
      duration: 100,
      timingFunction: 'ease',
    })

    this.animation = animation

    animation.translate(10).step()
    animation.translate(-10).step()
    animation.translate(0).step()

    this.setData({
      animationData: animation.export()
    })
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
    let t = this.data.learning_type
    if (this.finishedYet()) {
      wx.removeStorageSync(t + 'list')
      wx.removeStorageSync(t + 'vid')
      wx.setStorageSync('favorite_list', [...this.favorite_list].toString())
      return
    }
    wx.setStorageSync(t + 'list', this.view_list.toString())
    wx.setStorageSync(t + 'vid', this.vid)
    wx.setStorageSync('favorite_list', [...this.favorite_list].toString())
    return
  },

  getNextQuestion: function (step = 1) {
    this.vid += step
    //console.log(this.vid)
    this.vid = Math.min(this.vid, this.view_list.length - 1)
    let qid = this.view_list[this.vid]
    return this.data.questions[qid]
  },
  getPreviousQuestion: function (step = 1) {
    this.vid -= step
    this.vid = Math.max(this.vid, 0)
    let qid = this.view_list[this.vid]
    return this.data.questions[qid]
  },
  isFavorite: function () {
    let qid = this.view_list[this.vid]
    //console.log(this.favorite_list+"111");
    return this.favorite_list.has(qid)
  },
  toggleFavorite: function () {
    let qid = this.view_list[this.vid]
    if (this.favorite_list.has(qid)) {
      this.favorite_list.delete(qid)
      return false
    } else {
      this.favorite_list.add(qid)
      return true
    }
  },
  getQuestionCount: function (list) {
    //console.log(list.length)
    return list.length;

  },
  setFavoriteList: function (list) {
    this.favorite_list = new Set(list)
  },
  isWrong: function (qid) {
    return this.wrong_list.has(qid)
  },
  setWrongList: function (list) {
    this.wrong_list = new Set(list)
  },
  toggleWrong: function () {
    let qid = this.view_list[this.data.vid]
    if (this.data.favorite.has(qid)) {
      this.data.wrong_list.delete(qid)
      return false
    } else {
      this.data.wrong_list.add(qid)
      return true
    }
  },
  finishedYet: function () {
    return this.vid >= this.view_list.length - 1
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

  }
})