// suppress Node.js deprecation warnings
process.removeAllListeners('warning');
process.on('warning', (warning) => {
  if (warning.name === 'DeprecationWarning') {
    return;
  }
  console.warn(warning);
});

module.exports = function (config) {
  process.env.NODE_NO_WARNINGS = 1;
  process.env.NODE_OPTIONS = '--no-deprecation';

  config.set({
    browsers: ['ChromeHeadless'],
    basePath: 'public/test',
    files: ['ci.js'],
    frameworks: ['cljs-test'],
    plugins: ['karma-cljs-test', 'karma-chrome-launcher'],
    colors: true,
    logLevel: config.LOG_ERROR,
    client: {
      args: ["shadow.test.karma.init"],
      singleRun: true
    },
    proxies: {
      '/images/': '/base/images/'
    },
    webServer: {
      middleware: {
        'suppress-404': function (req, res, next) {
          const isImageRequest = req.url.match(/^\/base\/images\/.*\.(jpe?g|png|gif|webp)$/i);
          if (isImageRequest) {
            res.statusCode = 200;
            res.end();
          } else {
            next();
          }
        }
      }
    }
  })
};
