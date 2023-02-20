<template>
  <el-dialog
    :close-on-click-modal="false"
    :close-on-press-escape="true"
    :title="title"
    :type="type"
    :visible.sync="isVisible"
    :width="width"
    top="50px"
  >
    <el-form
      ref="form"
      :model="application"
      :rules="rules"
      label-position="right"
      label-width="100px"
    >
      <el-form-item v-show="type!=='add' " :label="$t('table.application.appKey')" prop="appKey">
        <el-input v-model="application.appKey" :disabled="type==='view' || type==='edit' " />
      </el-form-item>
      <el-form-item
        v-show="type!=='add' "
        :label="$t('table.application.appSecret')"
        prop="appSecret"
      >
        <el-input v-model="application.appSecret" :disabled="type==='view' || type==='edit' " />
      </el-form-item>
      <el-form-item :label="$t('table.application.name')" prop="name">
        <el-input v-model="application.name" :disabled="type==='view'" />
      </el-form-item>
      <el-form-item :label="$t('table.application.website')" prop="website">
        <el-input v-model="application.website" :disabled="type==='view'" />
      </el-form-item>
      <el-form-item :label="$t('table.application.icon')" prop="icon">
        <el-input v-model="application.icon" :disabled="type==='icon'" />
      </el-form-item>
      <el-form-item :label="$t('table.application.appType')" prop="appType">
        <el-radio-group v-model="application.appType.code" border="true" size="small">
          <el-radio-button
            v-for="(item, key, index) in enums.ApplicationAppTypeEnum"
            :key="index"
            :label="key"
            :value="key"
          >{{ item }}</el-radio-button>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="$t('table.application.status')" prop="status">
        <el-radio-group v-model="application.status" border="true" size="small">
          <el-radio-button :label="true">{{ $t('common.status.valid') }}</el-radio-button>
          <el-radio-button :label="false">{{ $t('common.status.invalid') }}</el-radio-button>
        </el-radio-group>
        <aside class="tips">禁用：提示"请求地址,禁止访问!";</aside>
      </el-form-item>
      <el-form-item :label="$t('table.application.describe')" prop="describe">
        <el-input v-model="application.describe" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button plain type="warning" @click="isVisible = false">{{ $t('common.cancel') }}</el-button>
      <el-button plain type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import applicationApi from '@/api/Application.js'

export default {
  name: 'ApplicationEdit',
  components: {},
  props: {
    dialogVisible: {
      type: Boolean,
      default: false
    },
    type: {
      type: String,
      default: 'add'
    }
  },
  data() {
    return {
      remoteApplicationLoading: false,
      application: this.initApplication(),
      screenWidth: 0,
      width: this.initWidth(),
      orgList: [],
      applicationList: [],
      rules: {
        name: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          },
          {
            min: 1,
            max: 255,
            message: this.$t('rules.range4to10'),
            trigger: 'blur'
          }
        ],
        status: {
          required: true,
          message: this.$t('rules.require'),
          trigger: 'blur'
        }
      },
      serviceList: [{ id: 'pd-auth-server', name: '权限服务' }]
    }
  },
  computed: {
    isVisible: {
      get() {
        return this.dialogVisible
      },
      set() {
        this.close()
        this.reset()
      }
    },
    enums() {
      return this.$store.state.common.enums
    },
    title() {
      return this.type === 'add'
        ? this.$t('common.add')
        : this.$t('common.edit')
    }
  },
  watch: {},
  mounted() {
    window.onresize = () => {
      return (() => {
        this.width = this.initWidth()
      })()
    }
  },
  methods: {
    initApplication() {
      return {
        id: '',
        appKey: '',
        appSecret: '',
        website: '',
        name: '',
        icon: '',
        appType: { code: 'SERVER' },
        describe: '',
        status: true
      }
    },
    initWidth() {
      this.screenWidth = document.body.clientWidth
      if (this.screenWidth < 991) {
        return '90%'
      } else if (this.screenWidth < 1400) {
        return '45%'
      } else {
        return '800px'
      }
    },
    loadListOptions({ callback }) {
      callback()
    },
    setApplication(val, orgs) {
      const vm = this
      vm.orgList = orgs
      if (val) {
        vm.application = { ...val }
      }
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.application = this.initApplication()
    },
    submitForm() {
      const vm = this
      this.$refs.form.validate(valid => {
        if (valid) {
          vm.editSubmit()
        } else {
          return false
        }
      })
    },
    editSubmit() {
      const vm = this
      if (vm.type === 'add') {
        vm.save()
      } else {
        vm.update()
      }
    },
    save() {
      const vm = this
      applicationApi.save(this.application).then(response => {
        const res = response.data
        if (res.isSuccess) {
          vm.isVisible = false
          vm.$message({
            message: vm.$t('tips.createSuccess'),
            type: 'success'
          })
          vm.$emit('success')
        }
      })
    },
    update() {
      applicationApi.update(this.application).then(response => {
        const res = response.data
        if (res.isSuccess) {
          this.isVisible = false
          this.$message({
            message: this.$t('tips.updateSuccess'),
            type: 'success'
          })
          this.$emit('success')
        }
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.tips {
  margin-bottom: 0;
  padding: 0px 10px;
}
</style>
