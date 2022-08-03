// components/theSwiper.js
Component({
  /**
   * 组件的属性列表
   */
  properties: {
    imgUrls: Array,
  },

  /**
   * 组件的初始数据
   */
  data: {
    indicatorDots: false,
    autoplay: false,
    interval: 3000,
    duration: 1000,
    currentIndex: 0
  },

  /**
   * 组件的方法列表
   */
  methods: {
    swiperChange(e) {
      //console.log(e);
      this.setData({
        currentIndex: e.detail.current
      });
    }
  }
});
