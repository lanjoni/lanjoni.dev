/** @type {import('tailwindcss').Config} */
module.exports = {
  mode: 'jit',
  content: [
    './public/js/app.js',
    './public/js/index.css',
    './src/dev/lanjoni/**/*.cljs'
  ],
  plugins: [
    require('daisyui'),
  ],
  daisyui: {
    themes: ["black", "lofi"],
  }
}
