const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: "http://localhost:8080/api",
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
