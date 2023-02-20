<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="queryParams.appKey"
        :placeholder="$t('table.application.appKey')"
        class="filter-item search-item"
      />
      <el-input
        v-model="queryParams.name"
        :placeholder="$t('table.application.name')"
        class="filter-item search-item"
      />

      <el-date-picker
        v-model="queryParams.timeRange"
        :range-separator="null"
        :start-placeholder="$t('table.createTime')"
        class="filter-item search-item date-range-item"
        format="yyyy-MM-dd HH:mm:ss"
        type="datetimerange"
        value-format="yyyy-MM-dd HH:mm:ss"
      />
      <el-button class="filter-item" plain type="primary" @click="search">{{ $t('table.search') }}</el-button>
      <el-button class="filter-item" plain type="warning" @click="reset">{{ $t('table.reset') }}</el-button>
      <el-dropdown
        v-has-any-permission="['application:delete','application:export']"
        class="filter-item"
        trigger="click"
      >
        <el-button>
          {{ $t('table.more') }}
          <i class="el-icon-arrow-down el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            v-has-permission="['application:add']"
            @click.native="add"
          >{{ $t('table.add') }}</el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['application:delete']"
            @click.native="batchDelete"
          >{{ $t('table.delete') }}</el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['application:export']"
            @click.native="exportExcel"
          >{{ $t('table.export') }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-table
      :key="tableKey"
      ref="table"
      v-loading="loading"
      :data="tableData.records"
      border
      fit
      style="width: 100%;"
      @cell-click="cellClick"
      @filter-change="filterChange"
      @selection-change="onSelectChange"
      @sort-change="sortChange"
    >
      <el-table-column align="center" type="selection" width="40px" />
      <el-table-column
        :label="$t('table.application.appKey')"
        :show-overflow-tooltip="true"
        align="center"
        prop="appKey"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.appKey }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.application.name')"
        :show-overflow-tooltip="true"
        align="left"
        min-width="120px"
        prop="name"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.application.website')"
        :show-overflow-tooltip="true"
        align="left"
        min-width="120px"
        prop="website"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.website }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.application.appType')"
        :show-overflow-tooltip="true"
        align="center"
        min-width="50px"
        prop="appType"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.appType ? scope.row.appType.desc : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.application.status')"
        :show-overflow-tooltip="true"
        align="center"
        prop="status"
        width="70px"
      >
        <template slot-scope="scope">
          <el-badge :type="scope.row.status ? 'success' :'danger'" class="status-item" is-dot />
          <span>{{ scope.row.status? $t('common.status.valid') : $t('common.status.invalid') }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.application.describe')"
        :show-overflow-tooltip="true"
        align="left"
        min-width="120px"
        prop="describe"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.describe }}</span>
        </template>
      </el-table-column>

      <el-table-column
        :label="$t('table.createTime')"
        :show-overflow-tooltip="true"
        align="center"
        prop="className"
        width="170px"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column
        :label="$t('table.operation')"
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
        width="110px"
      >
        <template slot-scope="{row}">
          <i
            v-has-permission="['application:edit']"
            class="el-icon-edit table-operation"
            style="color: #2db7f5;"
            @click="edit(row)"
          />
          <i
            v-has-permission="['application:delete']"
            class="el-icon-delete table-operation"
            style="color: #f50;"
            @click="singleDelete(row)"
          />
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="tableData.total>0"
      :limit.sync="pagination.size"
      :page.sync="pagination.current"
      :total="Number(tableData.total)"
      @pagination="fetch"
    />

    <application-edit
      ref="edit"
      :dialog-visible="dialog.isVisible"
      :type="dialog.type"
      @close="editClose"
      @success="editSuccess"
    />
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import ApplicationEdit from './Edit'
import applicationApi from '@/api/Application.js'

export default {
  name: 'Application',
  components: { Pagination, ApplicationEdit },
  filters: {},
  data() {
    return {
      dialog: {
        isVisible: false,
        type: 'add'
      },
      tableKey: 0,
      loading: false,
      queryParams: {},
      sort: {},
      selection: [],
      tableData: {},
      pagination: {
        size: 10,
        current: 1
      }
    }
  },
  computed: {},
  mounted() {
    this.fetch()
  },
  methods: {
    onSelectChange(selection) {
      this.selection = selection
    },
    exportExcel() {},

    fetch(params = {}) {
      params.current = this.pagination.current
      params.size = this.pagination.size
      if (this.queryParams.timeRange) {
        params.startCreateTime = this.queryParams.timeRange[0]
        params.endCreateTime = this.queryParams.timeRange[1]
      }
      this.loading = true

      applicationApi.page(params).then(response => {
        const res = response.data
        this.loading = false
        this.tableData = res.data
      })
    },
    singleDelete(row) {
      this.$refs.table.toggleRowSelection(row, true)
      this.batchDelete()
    },
    batchDelete() {
      if (!this.selection.length) {
        this.$message({
          message: this.$t('tips.noDataSelected'),
          type: 'warning'
        })
        return
      }

      const isPersist = this.selection.findIndex(item => item.isPersist)
      if (isPersist > -1) {
        this.$message({
          message: '不能删除内置数据',
          type: 'warning'
        })
        return
      }

      this.$confirm(this.$t('tips.confirmDelete'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      })
        .then(() => {
          const logIds = this.selection.map(item => item.id)
          this.delete(logIds)
        })
        .catch(() => {
          this.clearSelections()
        })
    },
    clearSelections() {
      this.$refs.table.clearSelection()
    },
    delete(logIds) {
      applicationApi.delete({ ids: logIds }).then(response => {
        const res = response.data
        if (res.isSuccess) {
          this.$message({
            message: this.$t('tips.deleteSuccess'),
            type: 'success'
          })
        }
        this.search()
      })
    },
    search() {
      this.fetch({
        ...this.queryParams,
        ...this.sort
      })
    },
    reset() {
      this.queryParams = {}
      this.sort = {}
      this.$refs.table.clearSort()
      this.$refs.table.clearFilter()
      this.search()
    },

    sortChange(val) {
      this.sort.field = val.prop
      this.sort.order = val.order
      this.search()
    },
    filterChange(filters) {
      for (const key in filters) {
        this.queryParams[key] = filters[key][0]
      }
      this.search()
    },

    closeDrawer(done) {
      done()
      this.currentRow = {}
    },

    cellClick(row, column) {
      var oInput = document.createElement('input') //创建一个隐藏input（重要！）
      oInput.value = row[column.property] //赋值

      document.body.appendChild(oInput)
      oInput.select() // 选择对象
      document.execCommand('Copy') // 执行浏览器复制命令
      oInput.className = 'oInput'
      oInput.style.display = 'none'
    },
    editClose() {
      this.dialog.isVisible = false
    },
    editSuccess() {
      this.search()
    },
    add() {
      this.dialog.type = 'add'
      this.dialog.isVisible = true
      this.$refs.edit.setApplication(false)
    },
    edit(row) {
      this.$refs.edit.setApplication(row)
      this.dialog.type = 'edit'
      this.dialog.isVisible = true
    }
  }
}
</script>
<style lang="scss" scoped>
.item {
  margin-top: 7px;
}

.box-item {
  margin-top: 15px;
  aside {
    word-wrap: break-word;
    margin-top: 15px;
  }
  pre {
    white-space: pre-wrap;
    font-size: 0.8em;
    line-height: 1.5em;
  }
}
</style>
