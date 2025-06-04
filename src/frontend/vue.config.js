
module.exports = {
  devServer: {
    port: 3000,
    proxy: {
      'vue/': {
        target: 'http://localhost:8082',
        ws: true,
        changeOrigin: true
      }

    }

  }
}
