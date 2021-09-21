<template>
  <div id="markdown" class="markdown-body">
    <vue-markdown :source="md"></vue-markdown>
  </div>
</template>

<script>
  import axios from 'axios';
  import VueMarkdown from "vue-markdown";
  export default {
    name: "BaseMarkdown",
    components: {
      VueMarkdown
    },
    data() {
      return {
      }
    },
    props:["md"]
  }
</script>
<style lang="scss" scoped>
  @import "../../assets/css/github-markdown.css";

  .markdown-body {
    box-sizing: border-box;
    margin: 0 auto;
    padding: 0 40px;
  }
</style>