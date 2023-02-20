<template>
  <div class="app-container">
    <div class="filter-container">
      <label style="color:#909399;font-weight:500;">账号：</label>
      <el-input
        v-model="queryParams.account"
        :placeholder="$t('table.loginLog.account')"
        class="filter-item search-item"
        style="width: 150px;"
      />
      <label style="color:#909399;font-weight:500;">地区：</label>
      <el-input
        v-model="queryParams.location"
        :placeholder="$t('table.loginLog.location')"
        class="filter-item search-item"
        style="width: 150px;"
      />
      <label style="color:#909399;font-weight:500;">IP：</label>
      <el-input
        v-model="queryParams.requestIp"
        :placeholder="$t('table.loginLog.requestIp')"
        class="filter-item search-item"
        style="width: 150px;"
      />
      <label style="color:#909399;font-weight:500;">创始时间：</label>
      <el-date-picker
        v-model="queryParams.timeRange"
        :range-separator="null"
        :start-placeholder="$t('table.createTime')"
        class="filter-item search-item date-range-item"
        format="yyyy-MM-dd HH:mm:ss"
        type="datetimerange"
        style="width: 300px;"
        value-format="yyyy-MM-dd HH:mm:ss"
      />
      <el-button
        style="background-color: #E05635;color: #fff;border-radius: 5px;border-color: #DCDFE6;"
        @click="search"
      >{{ $t("table.search") }}</el-button>
      <el-button
        style="background-color: #fff;color: #606266;border-radius: 5px;border-color: #DCDFE6;"
        @click="reset"
      >{{ $t("table.reset") }}</el-button>
      <el-dropdown
        v-has-any-permission="['loginLog:delete', 'loginLog:export']"
        class="filter-item"
        trigger="click"
      >
        <el-button
          style="height:40px;margin-top:6px;background-color: #fff;color: #606266;border-color: #DCDFE6"
        >
          {{ $t("table.more") }}
          <i class="el-icon-arrow-down el-icon--right" />
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            v-has-permission="['loginLog:delete']"
            @click.native="batchDelete"
          >{{ $t("table.delete") }}</el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['loginLog:export']"
            @click.native="exportExcel"
          >{{ $t("table.export") }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <el-card shadow="never" style="margin-top: 10px;">
      <el-table
        :key="tableKey"
        ref="table"
        v-loading="loading"
        :data="tableData.records"
        :header-cell-style="{background:'#FCFBFF',border:'0'}"
        fit
        style="width: 100%;"
        @selection-change="onSelectChange"
        @sort-change="sortChange"
      >
        <el-table-column align="center" type="selection" width="40px" />
        <el-table-column
          :label="$t('table.loginLog.userName')"
          :show-overflow-tooltip="true"
          align="center"
          min-width="80px"
          prop="userName"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.userName }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.requestIp')"
          :show-overflow-tooltip="true"
          align="center"
          min-width="80px"
          prop="requestIp"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.requestIp }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.browser')"
          :show-overflow-tooltip="true"
          align="center"
          prop="browser"
          width="120px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.browser }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.browserVersion')"
          :show-overflow-tooltip="true"
          align="center"
          prop="browserVersion"
          width="120px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.browserVersion }}</span>
          </template>
        </el-table-column>

        <el-table-column
          :label="$t('table.loginLog.operatingSystem')"
          :show-overflow-tooltip="true"
          align="center"
          prop="operatingSystem"
          width="170px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.operatingSystem }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.location')"
          :show-overflow-tooltip="true"
          align="center"
          min-width="150px"
          prop="location"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.location }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.loginDate')"
          :show-overflow-tooltip="true"
          align="center"
          prop="createTime"
          width="170px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.loginLog.description')"
          :show-overflow-tooltip="true"
          align="left"
          column-key="description"
          prop="description"
        >
          <template slot-scope="scope">
            <span>
              <el-badge
                :type="
                  scope.row.description && scope.row.description == '登录成功'
                    ? 'success'
                    : 'danger'
                "
                class="item"
                is-dot
              />
              {{ scope.row.description }}
            </span>
          </template>
        </el-table-column>

        <el-table-column
          :label="$t('table.operation')"
          align="center"
          class-name="small-padding fixed-width"
          width="100px"
        >
          <template slot-scope="{ row }">
            <i
              v-has-permission="['loginLog:delete']"
              style="color: #E05635;"
              @click="singleDelete(row)"
            >删除</i>

            <el-link
              v-has-no-permission="['loginLog:delete']"
              class="no-perm"
            >{{ $t("tips.noPermission") }}</el-link>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="tableData.total > 0"
        :limit.sync="pagination.size"
        :page.sync="pagination.current"
        :total="Number(tableData.total)"
        @pagination="fetch"
      />
    </el-card>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import loginLogApi from '@/api/LoginLog.js'

export default {
  name: 'LoginLog',
  components: { Pagination },
  filters: {},
  data() {
    return {
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

      loginLogApi.page(params).then(response => {
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
      loginLogApi.delete({ ids: logIds }).then(response => {
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
    }
  }
}
</script>
<style lang="scss">
.search-loginlog {
  background-color: #e05635;
  color: #fff;
  border-radius: 5px;
}
.reset-loginlog {
  background-color: #fff;
  color: #000;
  border-radius: 5px;
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
.item {
  margin-top: 7px;
}
</style>
