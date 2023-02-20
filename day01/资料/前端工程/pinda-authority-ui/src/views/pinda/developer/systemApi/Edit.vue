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
      :model="systemApi"
      :rules="rules"
      label-position="right"
      label-width="100px"
    >
      <el-form-item :label="$t('table.systemApi.serviceId')" prop="serviceId">
        <el-select
          v-model="systemApi.serviceId"
          :disabled="type==='view'"
          placeholder
          style="width:100%"
          value
        >
          <el-option
            v-for="item in serviceList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('table.systemApi.code')" prop="code">
        <el-input v-model="systemApi.code" :disabled="type==='view' || type==='edit' " />
      </el-form-item>
      <el-form-item :label="$t('table.systemApi.name')" prop="name">
        <el-input v-model="systemApi.name" :disabled="type==='view'" />
      </el-form-item>
      <el-form-item :label="$t('table.systemApi.path')" prop="path">
        <el-input v-model="systemApi.path" :disabled="type==='view'" />
      </el-form-item>
      <el-form-item :label="$t('table.systemApi.status')" prop="status">
        <el-radio-group v-model="systemApi.status" border="true" size="small">
          <el-radio-button :label="true">{{ $t('common.status.valid') }}</el-radio-button>
          <el-radio-button :label="false">{{ $t('common.status.invalid') }}</el-radio-button>
        </el-radio-group>
        <aside class="tips">禁用：提示"请求地址,禁止访问!";</aside>
      </el-form-item>
      <el-form-item :label="$t('table.systemApi.isAuth')" prop="isAuth">
        <el-radio-group v-model="systemApi.isAuth" size="small">
          <el-radio-button :label="true">{{ $t('common.yes') }}</el-radio-button>
          <el-radio-button :label="false">{{ $t('common.no') }}</el-radio-button>
        </el-radio-group>
        <aside class="tips">是：未认证登录,提示"认证失败,请重新登录!";否: 不需要认证登录</aside>
      </el-form-item>
      <!-- <el-form-item :label="$t('table.systemApi.isOpen')" prop="isOpen">
        <el-radio-group size="small" v-model="systemApi.isOpen">
          <el-radio-button :label="true">{{ $t('common.yes') }}</el-radio-button>
          <el-radio-button :label="false">{{ $t('common.no') }}</el-radio-button>
        </el-radio-group>
        <aside class="tips">否:提示"请求地址,拒绝访问!"</aside>
      </el-form-item>-->
      <el-form-item :label="$t('table.systemApi.describe')" prop="describe">
        <el-input v-model="systemApi.describe" />
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
import systemApiApi from '@/api/SystemApi.js'

export default {
  name: 'SystemApiEdit',
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
      remoteSystemApiLoading: false,
      systemApi: this.initSystemApi(),
      screenWidth: 0,
      width: this.initWidth(),
      orgList: [],
      systemApiList: [],
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
        },
        serviceId: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          },
          {
            min: 1,
            max: 50,
            message: this.$t('rules.range4to10'),
            trigger: 'blur'
          }
        ]
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
    initSystemApi() {
      return {
        id: '',
        code: '',
        describe: '',
        requestMethod: '',
        contentType: '',
        serviceId: '',
        path: '',
        status: true,
        isOpen: true,
        isAuth: false
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
    setSystemApi(val, orgs) {
      const vm = this
      vm.orgList = orgs
      if (val) {
        vm.systemApi = { ...val }
      }
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.systemApi = this.initSystemApi()
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
      systemApiApi.save(this.systemApi).then(response => {
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
      systemApiApi.update(this.systemApi).then(response => {
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
