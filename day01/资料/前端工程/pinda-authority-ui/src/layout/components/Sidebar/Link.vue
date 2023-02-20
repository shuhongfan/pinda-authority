
<template>
  <!-- eslint-disable vue/require-component-is -->
  <component v-bind="linkProps(to)">
    <slot />
  </component>
</template>

<script>
import { isExternal } from '@/utils/my-validate'

export default {
  props: {
    to: {
      type: String,
      required: true
    }
  },
  methods: {
    linkProps(url) {
      if (isExternal(url)) {
        // console.log(url)
        // let result = `/myiframe/urlPath?${objToform(params)}`
        // return {
        //   is: 'a',
        //   href: url,
        //   target: '_blank',
        //   rel: 'noopener'
        // }
        return {
          is: 'router-link',
          to: `/myiframe/iframe?urlPath&src=${url}`
        }
      }

      return {
        is: 'router-link',
        to: url
      }
    },
    objToform(obj) {
      let result = []
      Object.keys(obj).forEach(ele => {
        result.push(`${ele}=${obj[ele]}`)
      })
      return result.join('&')
    }
  }
}
</script>
