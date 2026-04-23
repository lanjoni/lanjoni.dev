# lanjoni.dev
> using [helix-refx-tailwind-example](https://github.com/lanjoni/helix-refx-tailwind-example) but with flex instead of refx

Static personal blog and markdown article website built using Helix, Flex and Tailwind CSS (with no backend).

# Prerequisites
Things you need installed to use this repository

- [Node.js](https://nodejs.dev/download)
- [Clojure](https://clojure.org/guides/getting_started)

## Install dependencies
```bash
npm install
```

## Run application with shadow-cljs
```bash
npm run dev
```

### After running `npm start`
- App available at http://localhost:5000
- Dev tools available at http://localhost:5002

## Structure
```
.
├── public
│   ├── css
│   │   └── index.css
│   ├── images
│   │   └── some pictures
│   ├── index.html
│   └── md
│       └── some articles
├── src
│   └── dev
│       └── lanjoni
│           ├── components
│           │   ├── link.cljs
│           │   ├── loading.cljs
│           │   └── markdown.cljs
│           ├── core.cljs
│           ├── infra
│           │   ├── flex
│           │   │   └── hook.cljs
│           │   ├── helix.cljc
│           │   ├── http
│           │   │   └── component.cljs
│           │   ├── http.cljs
│           │   ├── routes
│           │   │   ├── core.cljs
│           │   │   └── state.cljs
│           │   └── user_config
│           │       └── state.cljs
│           ├── panels
│           │   ├── about
│           │   │   ├── components.cljs
│           │   │   └── view.cljs
│           │   ├── content
│           │   │   ├── state.cljs
│           │   │   └── view.cljs
│           │   ├── error
│           │   │   └── view.cljs
│           │   ├── home
│           │   │   └── view.cljs
│           │   ├── shell
│           │   │   ├── components.cljs
│           │   │   └── view.cljs
│           │   └── writing
│           │       └── view.cljs
│           ├── routes.cljs
│           └── utils.cljs
├── deps.edn
├── package.json
├── shadow-cljs.edn
└── tailwind.config.js
```
- To add more articles you can put your `md` file into `public/md`. To see an article you can access `localhost:5000/#/writing/your-article-file-name`. This article will be parsed in real time using [react-markdown](https://github.com/remarkjs/react-markdown);
- To add more dependencies you can update both `deps.edn` for Clojure dependencies or `package.json` for NPM dependencies;
- To configure your [daisyUI](https://daisyui.com/) you can modify your `tailwind.config.js`;
- The separation structure of page rendering is: `views -> panels -> components` or `views -> components`;

## Used software
- [shadow-cljs](https://github.com/thheller/shadow-cljs)
- [helix](https://github.com/lilactown/helix)
- [flex](https://github.com/lilactown/flex)
- [tailwind css](https://github.com/tailwindlabs/tailwindcss)

# License
This is free and unencumbered software released into the public domain.  
For more information, please refer to <http://unlicense.org>
