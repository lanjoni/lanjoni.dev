# lanjoni.dev
> using [helix-refx-tailwind-example](https://github.com/lanjoni/helix-refx-tailwind-example)

Static personal blog and markdown article website built using Helix, Refx and Tailwind CSS (with no backend).

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
- App available at http://localhost:8200
- Dev tools available at http://localhost:8300
- nREPL available at port 8777

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
│           │   ├── content_list.cljs
│           │   ├── content_topic.cljs
│           │   ├── link.cljs
│           │   ├── markdown.cljs
│           │   ├── navbar_items.cljs
│           │   └── theme_controller.cljs
│           ├── core.cljs
│           ├── events.cljs
│           ├── hooks.cljs
│           ├── panels
│           │   ├── footer.cljs
│           │   ├── landing.cljs
│           │   └── navbar.cljs
│           ├── routes.cljs
│           ├── subs.cljs
│           ├── utils.cljs
│           └── views
│               ├── about.cljs
│               ├── content.cljs
│               ├── home.cljs
│               └── writing.cljs
├── deps.edn
├── package.json
├── shadow-cljs.edn
└── tailwind.config.js
```
- To add more articles you can put your `md` file into `public/md`. To see an article you can access `localhost:8200/#/writing/your-article-file-name`. This article will be parsed in real time using [react-markdown](https://github.com/remarkjs/react-markdown);
- To add more dependencies you can update both `deps.edn` for Clojure dependencies or `package.json` for NPM dependencies;
- To configure your [daisyUI](https://daisyui.com/) you can modify your `tailwind.config.js`;
- The separation structure of page rendering is: `views -> panels -> components` or `views -> components`;

## Used software
- [shadow-cljs](https://github.com/thheller/shadow-cljs)
- [helix](https://github.com/lilactown/helix)
- [refx](https://github.com/ferdinand-beyer/refx)
- [tailwind css](https://github.com/tailwindlabs/tailwindcss)

## TODO
- [ ] Test: add sample tests

# License
This is free and unencumbered software released into the public domain.  
For more information, please refer to <http://unlicense.org>
