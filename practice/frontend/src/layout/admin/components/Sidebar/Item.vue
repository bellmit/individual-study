<script>
export default {
  name: 'MenuItem',
  functional: true,
  props: {
    icon: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    }
  },
  render(h, context) {
    const { icon, title } = context.props
    const vnodes = []

    if (icon) {
      let elIcon = "el-icon-" + icon;
      vnodes.push(<i class={elIcon}></i>)
      // elIcon = 'icon-' + icon;
      // vnodes.push(<svg-icon icon-class={elIcon}></svg-icon>)
    }

    if (title) {
      vnodes.push(<span slot='title'>{(title)}</span>)
    }
    return vnodes
  }
}
</script>
