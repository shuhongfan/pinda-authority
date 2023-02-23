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
    <el-form ref="form" :model="goodsInfo" :rules="rules" label-position="right" label-width="150px">
      <el-form-item :label="$t('table.goodsInfo.name')" prop="name">
        <el-input v-model="goodsInfo.name" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.code')" prop="code">
        <el-input v-model="goodsInfo.code" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.price')" prop="price">
        <el-input v-model="goodsInfo.price" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.averageCost')" prop="averageCost">
        <el-input v-model="goodsInfo.averageCost" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.publishStatus')" prop="publishStatus">
        <el-radio-group v-model="goodsInfo.publishStatus">
          <el-radio :label="0">{{ $t('table.goodsInfo.publishStatusEnum._0')  }}</el-radio>
          <el-radio :label="1">{{ $t('table.goodsInfo.publishStatusEnum._1')  }}</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.auditStatus')" prop="auditStatus">
        <el-radio-group v-model="goodsInfo.auditStatus">
          <el-radio :label="0">{{ $t('table.goodsInfo.auditStatusEnum._0') }}</el-radio>
          <el-radio :label="1">{{ $t('table.goodsInfo.auditStatusEnum._1')  }}</el-radio>
        </el-radio-group></el-form-item>
      <el-form-item :label="$t('table.goodsInfo.weight')" prop="weight">
        <el-input v-model="goodsInfo.weight" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.length')" prop="length">
        <el-input v-model="goodsInfo.length" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.height')" prop="height">
        <el-input v-model="goodsInfo.height" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.width')" prop="width">
        <el-input v-model="goodsInfo.width" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.color')" prop="color">
        <el-input v-model="goodsInfo.color" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.productionDate')" prop="productionDate">
        <el-date-picker v-model="goodsInfo.productionDate" type="datetime" :placeholder="$t('table.goodsInfo.productionDate')" @change="dataSearch" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.shelfLife')" prop="shelfLife">
        <el-input v-model="goodsInfo.shelfLife" />
      </el-form-item>
      <el-form-item :label="$t('table.goodsInfo.descript')" prop="descript">
        <el-input v-model="goodsInfo.descript" />
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button plain type="warning" @click="isVisible = false">{{ $t('common.cancel') }}</el-button>
      <el-button plain type="primary" @click="submitForm">{{ $t('common.confirm') }}</el-button>
    </div>
  </el-dialog>
</template>
<script>
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import goodsInfoApi from '@/api/GoodsInfo.js'

export default {
  name: 'goodsInfoEdit',
  components: { Treeselect },
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
      remotegoodsInfoLoading: false,
      goodsInfo: this.initgoodsInfo(),
      screenWidth: 0,
      width: this.initWidth(),
      orgList: [],
      goodsInfoList: [],
      rules: {
        name: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        code: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        price: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        averageCost: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        publishStatus: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        auditStatus: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        weight: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        length: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        height: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        width: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        color: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        productionDate: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ],
        shelfLife: [
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          }
        ]
      }
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
    initgoodsInfo() {
      return {

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
    setgoodsInfo(val, orgs) {
      const vm = this
      vm.orgList = orgs
      if (val) {
        vm.goodsInfo = { ...val }
      }
    },
    close() {
      this.$emit('close')
    },
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.goodsInfo = this.initgoodsInfo()
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
      goodsInfoApi.save(this.goodsInfo).then(response => {
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
      goodsInfoApi.update(this.goodsInfo).then(response => {
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
</style>
