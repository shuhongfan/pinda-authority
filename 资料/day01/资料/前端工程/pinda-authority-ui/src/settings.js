module.exports = {
  title: '微服务权限管理平台',
  onlinePreview: 'http://127.0.0.1:8012/onlinePreview?url=',
  druid: {
    authority: {
      "development": 'http://127.0.0.1:8764/druid',
      "production": 'http://39.100.244.120:8764/druid',
    },
    file: {
      "development": 'http://127.0.0.1:8765/druid',
      "production": 'http://39.100.244.120:8765/druid',
    }
  }
}
