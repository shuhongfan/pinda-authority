<template>
  <el-card style="margin:10px 10px 10px 10px">
    <iframe ref="iframe" :src="src" class="iframe" scrolling="auto"></iframe>
  </el-card>
</template>

<script>
export default {
  data() {
    return {
      src: '',
      urlPath: this.getUrlPath() //iframe src 路径
    }
  },
  computed: {},
  watch: {
    $route: function() {
      this.load()
    },
    routerPath: function() {
      // 监听routerPath变化，改变src路径
      this.urlPath = this.getUrlPath()
      alert(this.urlPath)
    }
  },
  created() {},
  mounted() {
    this.load()
    this.resize()
  },
  methods: {
    resize() {
      window.onresize = () => {
        this.iframeInit()
      }
    },
    load() {
      var flag = true //URL是否包含问号
      if (
        this.$route.query.src !== undefined &&
        this.$route.query.src.indexOf('?') === -1
      ) {
        flag = false
      }
      var list = []
      for (var key in this.$route.query) {
        if (key !== 'src' && key !== 'name' && key !== 'i18n') {
          list.push(`${key}= this.$route.query[key]`)
        }
      }
      list = list.join('&').toString()
      if (flag) {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? `&list` : ''
        }`
      } else {
        this.$route.query.src = `${this.$route.query.src}${
          list.length > 0 ? `?list` : ''
        }`
      }
      //超时3s自动隐藏等待狂，加强用户体验
      let time = 3
      const timeFunc = setInterval(() => {
        time--
        if (time === 0) {
          clearInterval(timeFunc)
        }
      }, 1000)
      this.iframeInit()
      this.src = this.getUrlPath()
    },
    iframeInit() {
      const iframe = this.$refs.iframe
      const clientHeight =
        document.documentElement.clientHeight - (screen > 1 ? 200 : 130)
      if (!iframe) return
      iframe.style.height = `${clientHeight}px`
      if (iframe.attachEvent) {
        iframe.attachEvent('onload', () => {})
      } else {
        iframe.onload = () => {}
      }
    },
    getUrlPath: function() {
      console.log(this.$route)
      //获取 iframe src 路径
      let url = decodeURIComponent(window.location.href)
      let urlH = ''
      if (window.location.port) {
        urlH = window.location.protocol + '//' + window.location.host + '/#'
      } else {
        urlH = window.location.protocol + '//' + window.location.hostname + '/#'
      }
      url = url.replace(urlH + '/myiframe/iframe?urlPath&src=', '')
      return url
    }
  }
}
</script>
<style lang="scss">
.iframe {
  width: 100%;
  height: 100%;
  border: 0;
  overflow: hidden;
}
</style>
