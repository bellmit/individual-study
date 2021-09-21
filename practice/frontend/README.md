# frontend

English | [简体中文](./README-zh.md)

> frontend project

## Build Setup


```bash
# install dependency
npm install

# develop
npm run dev
```

This will automatically open http://localhost:9528

## Build

```bash
# build for test environment
npm run build:stage

# build for production environment
npm run build:prod
```

## Advanced

```bash
# preview the release environment effect
npm run preview

# preview the release environment effect + static resource analysis
npm run preview -- --report

# code format check
npm run lint

# code format check and auto fix
npm run lint -- --fix
```

## charts components
# https://echarts.apache.org/examples/zh/index.html
npm install echarts@4.9.0 --save
npm install vue-echarts@4.1.0 --save