const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: "https://k7b309.p.ssafy.io:8080/api",
  },
});

// module.exports = {
//   transpileDependencies: true,
//   devServer: {
//     proxy: {
//       "/": {
//         target: "http://localhost:8080/api",
//         changeOrigin: true,
//       },
//     },
//   },
// };
