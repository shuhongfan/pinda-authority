<template>
  <div class="menu">
    <el-row :gutter="10">
      <el-col :sm="7" :xs="24">
        <div class="app-container">
          <div class="filter-container-menu">
            <el-input
              v-model="name"
              :placeholder="$t('table.menu.name')"
              class="filter-item search-item"
              clearable
              @keyup.enter.native="search"
            />
            <el-button
              style="background-color: #E05635;color: #fff;border-radius: 5px;border-color: #DCDFE6;"
              @click="search"
            >{{ $t('table.search') }}&nbsp;</el-button>
            <el-button
              style="background-color: #fff;color: #606266;border-radius: 5px;border-color: #DCDFE6;"
              @click="reset"
            >{{ $t('table.reset') }}</el-button>
            <el-tooltip
              class="item"
              style="margin-top: 10px;"
              content="新增/删除时，请先勾选菜单"
              effect="dark"
              placement="right"
            >
              <el-dropdown
                v-has-any-permission="['menu:add','menu:delete','menu:export']"
                trigger="click"
              >
                <el-button
                  style="height:40px;margin-top:6px;background-color: #fff;color: #606266;border-color: #DCDFE6"
                >
                  {{ $t('table.more') }}
                  <i class="el-icon-arrow-down el-icon--right" />
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-has-permission="['menu:add']"
                    @click.native="add"
                  >{{ $t('table.add') }}</el-dropdown-item>
                  <el-dropdown-item
                    v-has-permission="['menu:delete']"
                    @click.native="deleteMenu"
                  >{{ $t('table.delete') }}</el-dropdown-item>
                  <el-dropdown-item
                    v-has-permission="['menu:export']"
                    @click.native="exportExcel"
                  >{{ $t('table.export') }}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </el-tooltip>
          </div>
          <commonTree ref="menuTree" :tree-data="menuTree" @nodeClick="nodeClick">
            <template slot-scope="treeNode">
              <span class="tree-icon">
                <i :class="treeNode.data.icon ? treeNode.data.icon : 'el-icon-document'" />
              </span>
              <span class="tree-icon">
                <el-badge
                  :type="treeNode.data.isEnable ? 'success' :'danger'"
                  class="status-item"
                  is-dot
                />
              </span>
            </template>
          </commonTree>
        </div>
      </el-col>
      <el-col :sm="8" :xs="24">
        <el-card class="box-card">
          <div slot="header" class="clearfix" style="background-color:'#FCFBFF">
            <span>{{ menu.id === '' ? this.$t('common.add') : this.$t('common.edit') }}</span>
          </div>
          <div>
            <el-form
              ref="form"
              :model="menu"
              :rules="rules"
              label-position="right"
              label-width="100px"
            >
              <el-form-item :label="$t('table.menu.parentId')" prop="parentId">
                <el-tooltip
                  :content="$t('tips.topId')"
                  class="item"
                  effect="dark"
                  placement="right"
                >
                  <el-input v-model="menu.parentId" readonly />
                </el-tooltip>
              </el-form-item>
              <el-form-item :label="$t('table.menu.name')" prop="name">
                <el-input v-model="menu.name" />
              </el-form-item>
              <!-- <el-form-item :label='$t("table.menu.type")' prop='type'>
                <el-radio-group v-model='menu.type'>
                  <el-radio label='DIR'>目录</el-radio>
                  <el-radio label='MENU'>菜单</el-radio>
                </el-radio-group>
              </el-form-item>-->
              <el-form-item :label="$t('table.menu.path')" prop="path">
                <el-input v-model="menu.path" @keyup.native="menuPath" />
                <span>{{ menuComponent }}</span>
              </el-form-item>
              <el-form-item :label="$t('table.menu.icon')" prop="icon">
                <el-input v-model="menu.icon">
                  <el-button
                    slot="append"
                    icon="el-icon-brush"
                    style="padding-left: 0;"
                    @click="chooseIcons"
                  />
                </el-input>
              </el-form-item>
              <el-row>
                <el-col :span="17">
                  <el-form-item :label="$t('table.status')" prop="isEnable">
                    <el-switch
                      v-model="menu.isEnable"
                      :active-text="$t('common.status.valid')"
                      :inactive-text="$t('common.status.invalid')"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item :label="$t('table.menu.isPublic')" prop="isPublic">
                    <el-switch
                      v-model="menu.isPublic"
                      :active-text="$t('common.yes')"
                      :inactive-text="$t('common.no')"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-form-item :label="$t('table.menu.sortValue')" prop="sortValue">
                <el-input-number
                  v-model="menu.sortValue"
                  :max="100"
                  :min="0"
                  @change="handleNumChange"
                />
              </el-form-item>
              <el-form-item :label="$t('table.menu.group')" prop="group">
                <el-tooltip class="item" content="用于区分多组菜单" effect="dark" placement="right">
                  <el-input v-model="menu.group" />
                </el-tooltip>
              </el-form-item>
              <el-form-item :label="$t('table.menu.describe')" prop="describe">
                <el-input v-model="menu.describe" />
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        <el-card class="box-card" style="margin-top: -2rem;">
          <el-row>
            <el-col :span="24" style="text-align: center">
              <el-button
                style="background-color: #E05635;color: #fff;"
                @click="submit"
              >{{ menu.id === '' ? this.$t('common.add') : this.$t('common.edit') }}</el-button>
            </el-col>
          </el-row>
        </el-card>
      </el-col>

      <el-col :sm="9" :xs="24">
        <el-card class="box-card">
          <div class="app-container">
            <div class="filter-container">
              <el-input
                v-model="resourceQueryParams.code"
                :placeholder="$t('table.resource.code')"
                class="filter-item search-item"
                style="width: 155px;"
              />
              <el-input
                v-model="resourceQueryParams.name"
                :placeholder="$t('table.resource.name')"
                class="filter-item search-item"
                style="width: 155px;"
              />
              <el-button
                style="background-color: #E05635;color: #fff;border-radius: 5px;border-color: #DCDFE6;"
                @click="resourceSearch"
              >{{ $t('table.search') }}</el-button>
              <el-dropdown
                v-has-any-permission="['resource:add','resource:delete']"
                class="filter-item"
                trigger="click"
              >
                <el-button
                  style="height:40px;margin-top:6px;background-color: #fff;color: #606266;border-color: #DCDFE6"
                >
                  {{ $t('table.more') }}
                  <i class="el-icon-arrow-down el-icon--right" />
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    v-has-permission="['resource:add']"
                    :disabled="!menu.id"
                    @click.native="resourceAdd"
                  >{{ $t('table.add') }}</el-dropdown-item>
                  <el-dropdown-item
                    v-has-permission="['resource:delete']"
                    @click.native="resourceBatchDelete"
                  >{{ $t('table.delete') }}</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>

            <el-table
              :key="resourceTableKey"
              ref="resourceTable"
              v-loading="resourceLoading"
              :data="resourceTableData.records"
              fit
              :header-cell-style="{background:'#FCFBFF',border:'0'}"
              style="width: 100%;"
              @selection-change="onResourceSelectChange"
              @sort-change="resourceSortChange"
            >
              <el-table-column align="center" type="selection" width="40px" />
              <el-table-column
                :label="$t('table.resource.code')"
                :show-overflow-tooltip="true"
                align="center"
                prop="code"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.code }}</span>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('table.resource.name')"
                :show-overflow-tooltip="true"
                align="center"
                prop="name"
              >
                <template slot-scope="scope">
                  <span>{{ scope.row.name }}</span>
                </template>
              </el-table-column>
              <el-table-column
                :label="$t('table.operation')"
                align="center"
                class-name="small-padding fixed-width"
                width="100px"
              >
                <template slot-scope="{row}">
                  <i
                    v-hasPermission="['resource:update']"
                    style="color: #009EFF;"
                    @click="resourceEdit(row)"
                  >修改</i>
                  <el-divider direction="vertical"></el-divider>
                  <i
                    v-hasPermission="['resource:delete']"
                    style="color: #E05635;"
                    @click="resourceSingleDelete(row)"
                  >删除</i>
                  <el-link
                    v-has-no-permission="['resource:update','resource:delete']"
                    class="no-perm"
                  >{{ $t('tips.noPermission') }}</el-link>
                </template>
              </el-table-column>
            </el-table>
            <pagination
              v-show="resourceTableData.total>0"
              :limit.sync="resourcePagination.size"
              :page.sync="resourcePagination.current"
              :total="Number(resourceTableData.total)"
              @pagination="resourceFetch"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>
    <Icons :dialog-visible="iconVisible" @choose="chooseIcon" @close="iconVisible = false" />
    <resource-edit
      ref="resourceEdit"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="resourceEditClose"
      @success="resourceEditSuccess"
    />
  </div>
</template>
<script>
import commonTree from '@/components/pinda/CommonTree.vue'
import Icons from './Icons'
import ResourceEdit from './Edit'
import Pagination from '@/components/Pagination'
import menuApi from '@/api/Menu.js'
import resourceApi from '@/api/Resource.js'

export default {
  name: 'MenuManage',
  components: { Icons, commonTree, Pagination, ResourceEdit },
  data() {
    return {
      dialog: {
        isVisible: false,
        type: 'add'
      },
      iconVisible: false,
      menuTree: [],
      name: '',
      menu: this.initMenu(),
      resourceQueryParams: {
        menuId: null
      },
      resourceTableKey: 0,
      resourceSort: {},
      resourceLoading: false,
      resourceSelection: [],
      resourceTableData: {
        total: 0
      },
      resourcePagination: {
        size: 10,
        current: 1
      },
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
            message: this.$t('rules.range2to10'),
            trigger: 'blur'
          }
        ],
        path: [
          {
            max: 255,
            message: this.$t('rules.noMoreThan100'),
            trigger: 'blur'
          },
          {
            required: true,
            message: this.$t('rules.require'),
            trigger: 'blur'
          },
          {
            validator: (rule, value, callback) => {
              const isUrl = this.isUrl(this.menu.path)

              if (value === '/' || (!isUrl && value.endsWith('/'))) {
                callback('请填写有效的路由地址')
              } else {
                callback()
              }
            },
            trigger: 'blur'
          }
        ]
      }
    }
  },
  computed: {
    menuComponent() {
      let comp = ''
      if (this.menu.path && this.menu.path !== '/') {
        const isUrl = this.isUrl(this.menu.path)
        if (isUrl) {
          comp = `跳转地址：${this.menu.path}`
        } else {
          comp = `前端组件：pinda${this.menu.path}/Index.vue`
        }
      } else {
        comp = `前端组件：pinda/Index.vue`
      }
      return comp
    }
  },
  watch: {
    'menu.path': function() {
      this.computedComponent()
    }
  },
  mounted() {
    this.initMenuTree()
  },
  methods: {
    isUrl(path) {
      const urls = [
        'http://',
        '/http://',
        'https://',
        '/https://',
        'www.',
        '/www.'
      ]
      const urlIndex = urls.findIndex(item => {
        return path.startsWith(item)
      })
      return urlIndex >= 0
    },
    menuPath() {
      const isUrl = this.isUrl(this.menu.path)
      if (!isUrl && !this.menu.path.startsWith('/')) {
        this.menu.path = '/' + this.menu.path
      } else if (isUrl) {
        if (this.menu.path.startsWith('/')) {
          this.menu.path = this.menu.path.substr(1)
        }
      }
    },
    computedComponent() {
      const isUrl = this.isUrl(this.menu.path)
      if (isUrl) {
        this.menu.component = 'Layout'
      } else {
        if (this.menu.path) {
          this.menu.component = `pinda${this.menu.path}/Index`
        } else {
          this.menu.component = `pinda/Index`
        }
      }
    },
    initMenuTree() {
      menuApi.allTree().then(response => {
        const res = response.data
        this.menuTree = res.data
      })
    },
    initMenu() {
      return {
        id: '',
        name: '',
        describe: '',
        code: '',
        isPublic: false,
        path: '',
        component: '',
        isEnable: true,
        sortValue: '',
        parentId: 0,
        icon: '',
        group: ''
      }
    },
    exportExcel() {
      this.$message({
        message: '带完善',
        type: 'success'
      })
    },
    nodeClick(data) {
      this.menu = { ...data }
      this.menu.name = this.menu.label
      this.$refs.form.clearValidate()

      this.resourceQueryParams.menuId = data.id
      this.resourceSearch()
    },
    handleNumChange(val) {
      this.menu.sortValue = val
    },
    chooseIcons() {
      this.iconVisible = true
    },
    chooseIcon(icon) {
      this.menu.icon = icon
      this.iconVisible = false
    },
    submit() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.menu.createTime = this.menu.updateTime = null
          if (this.menu.id) {
            this.update()
          } else {
            this.save()
          }
        } else {
          return false
        }
      })
    },
    save() {
      console.log(this.menu.component)
      menuApi.save(this.menu).then(response => {
        const res = response.data
        if (res.isSuccess) {
          this.$message({
            message: this.$t('tips.createSuccess'),
            type: 'success'
          })
        }
        this.reset()
      })
    },
    update() {
      console.log(this.menu)
      menuApi.update(this.menu).then(response => {
        const res = response.data
        if (res.isSuccess) {
          this.$message({
            message: this.$t('tips.updateSuccess'),
            type: 'success'
          })
        }
        this.reset()
      })
    },
    reset() {
      this.initMenuTree()
      this.name = ''
      this.resetForm()
    },
    search() {
      this.$refs.menuTree.$refs.treeRef.filter(this.name)
    },
    add() {
      this.resetForm()
      const checked = this.$refs.menuTree.$refs.treeRef.getCheckedKeys()
      if (checked.length > 1) {
        this.$message({
          message: this.$t('tips.onlyChooseOne'),
          type: 'warning'
        })
      } else if (checked.length > 0) {
        this.menu.parentId = checked[0]
      } else {
        this.menu.parentId = 0
      }
      this.resourceQueryParams.menuId = null
      this.resourceReset()
    },
    deleteMenu() {
      const checked = this.$refs.menuTree.$refs.treeRef.getCheckedKeys()
      if (checked.length === 0) {
        this.$message({
          message: this.$t('tips.noNodeSelected'),
          type: 'warning'
        })
      } else {
        this.$confirm(
          this.$t('tips.confirmDeleteNode'),
          this.$t('common.tips'),
          {
            confirmButtonText: this.$t('common.confirm'),
            cancelButtonText: this.$t('common.cancel'),
            type: 'warning'
          }
        )
          .then(() => {
            menuApi.delete({ ids: checked }).then(response => {
              const res = response.data
              if (res.isSuccess) {
                this.$message({
                  message: this.$t('tips.deleteSuccess'),
                  type: 'success'
                })
              }
              this.reset()
              this.resourceQueryParams.menuId = null
              this.resourceReset()
            })
          })
          .catch(() => {
            this.$refs.menuTree.$refs.treeRef.setCheckedKeys([])
          })
      }
    },
    resetForm() {
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.menu = this.initMenu()
    },
    resourceAdd() {
      this.dialog.type = 'add'
      this.dialog.isVisible = true
      this.$refs.resourceEdit.setResource({
        menuId: this.menu.id
      })
    },
    resourceEdit(row) {
      this.dialog.type = 'edit'
      this.dialog.isVisible = true
      row.menuId = this.menu.id
      this.$refs.resourceEdit.setResource(row)
    },
    resourceSingleDelete(row) {
      this.$refs.resourceTable.toggleRowSelection(row, true)
      this.resourceBatchDelete()
    },
    resourceBatchDelete() {
      if (!this.resourceSelection.length) {
        this.$message({
          message: this.$t('tips.noDataSelected'),
          type: 'warning'
        })
        return
      }
      this.$confirm(this.$t('tips.confirmDeleteNode'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      }).then(() => {
        const ids = this.resourceSelection.map(item => item.id)
        resourceApi.delete({ ids: ids }).then(response => {
          const res = response.data
          if (res.isSuccess) {
            this.$message({
              message: this.$t('tips.deleteSuccess'),
              type: 'success'
            })
          }
          this.resourceReset()
        })
      })
    },
    resourceReset() {
      this.resourceQueryParams = {
        menuId: this.resourceQueryParams.menuId
      }
      this.resourceSort = {}
      this.$refs.resourceTable.clearSort()
      this.$refs.resourceTable.clearFilter()
      this.resourceSearch()
    },
    resourceSearch() {
      this.resourceFetch({
        ...this.resourceQueryParams,
        ...this.resourceSort
      })
    },
    resourceFetch(params = {}) {
      params.size = this.resourcePagination.size
      params.current = this.resourcePagination.current

      params = { ...this.resourceQueryParams, ...params }
      if (params.menuId) {
        this.resourceLoading = true
        resourceApi.page(params).then(response => {
          const res = response.data
          this.resourceTableData = res.data
          this.resourceLoading = false
        })
      } else {
        this.resourceTableData = {}
      }
    },
    resourceSortChange(val) {
      this.resourceSort.field = val.prop
      this.resourceSort.order = val.order
      this.resourceSearch()
    },
    onResourceSelectChange(selection) {
      this.resourceSelection = selection
    },
    resourceEditClose() {
      this.dialog.isVisible = false
    },
    resourceEditSuccess() {
      this.resourceSearch()
    }
  }
}
</script>
<style lang="scss">
.el-button:hover,
.el-button:focus,
.search {
  background-color: #e05635;
}
.el-button:hover,
.el-button:focus,
.reset {
  background-color: #fff;
}
.menu {
  margin: 10px;
  .app-container {
    margin: 0 0 10px 0 !important;
  }
}
.el-card.is-always-shadow {
  box-shadow: none;
}
.el-card {
  border-radius: 0;
  border: none;
  .el-card__header {
    padding: 10px 20px !important;
    border-bottom: 1px solid #f1f1f1 !important;
  }
}
.el-table {
  border: 1px solid #f7f6f9;
}
.el-table tr,
.el-table td {
  border-top: 0;
  border-right: 0;
  border-bottom: 1px solid #f7f6f9;
}
.common-tree .el-tree-node__content .custom-tree-node {
  width: 70%;
}
</style>
