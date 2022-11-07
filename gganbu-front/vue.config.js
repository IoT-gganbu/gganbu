const { defineConfig } = require("@vue/cli-service");
module.exports = defineConfig({
  transpileDependencies: true,
});

// module.exports = {
//   devServer: {
//     proxy: {
//       "/": {
//         target: "http://localhost:8080/api",
//         changeOrigin: true,
//       },
//     },
//   },
// };
