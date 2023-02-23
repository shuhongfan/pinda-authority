<template>
  <div class="app-container">
    <div class="filter-container">
      <label style="color:#909399;font-weight:500;">商品名称：</label>
      <el-input
        v-model="queryParams.name"
        :placeholder="$t('table.goodsInfo.name')"
        class="filter-item search-item"
      />
      <label style="color:#909399;font-weight:500;">商品编码：</label>
      <el-input
        v-model="queryParams.code"
        :placeholder="$t('table.goodsInfo.code')"
        class="filter-item search-item"
      />
      <label style="color:#909399;font-weight:500;">日期：</label>
      <el-date-picker
        v-model="queryParams.timeRange"
        :range-separator="null"
        class="filter-item search-item date-range-item"
        end-placeholder="结束日期"
        format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        type="daterange"
        value-format="yyyy-MM-dd HH:mm:ss"
      />
      <el-button
        style="background-color: #E05635;color: #fff;border-radius: 5px;border-color: #DCDFE6;"
        @click="search"
      >{{ $t('table.search') }}
      </el-button>
      <el-button
        style="background-color: #fff;color: #606266;;border-radius:5px;border-color: #DCDFE6;"
        @click="reset"
      >{{ $t('table.reset') }}
      </el-button>
      <el-dropdown
        v-has-any-permission="['goodsInfo:add','goodsInfo:delete','goodsInfo:export']"
        class="filter-item"
        trigger="click"
      >
        <el-button
          style="height:40px;margin-top:6px;background-color: #fff;color: #606266;border-color: #DCDFE6"
        >
          {{ $t('table.more') }}
          <i class="el-icon-arrow-down el-icon--right"/>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item
            v-has-permission="['goodsInfo:add']"
            @click.native="add"
          >{{ $t('table.add') }}
          </el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['goodsInfo:delete']"
            @click.native="batchDelete"
          >{{ $t('table.delete') }}
          </el-dropdown-item>
          <el-dropdown-item
            v-has-permission="['goodsInfo:export']"
            @click.native="exportExcel"
          >{{ $t('table.export') }}
          </el-dropdown-item>
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
        <el-table-column align="center" type="selection" width="40px"/>

        <el-table-column
          :label="$t('table.goodsInfo.name')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.code')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.code }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.price')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.price }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.averageCost')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.averageCost }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.publishStatus')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.publishStatus ? $t('table.goodsInfo.publishStatusEnum._1') : $t('table.goodsInfo.publishStatusEnum._0') }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.auditStatus')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.auditStatus ? $t('table.goodsInfo.auditStatusEnum._1') : $t('table.goodsInfo.auditStatusEnum._0') }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.weight')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.weight }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.length')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.length }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.height')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.height }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.width')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.width }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.color')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.color }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.productionDate')"
          align="center"
          prop="createTime"
          width="170px"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.productionDate }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.shelfLife')"
          align="center"
          prop="createTime"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.shelfLife }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.goodsInfo.createTime')"
          align="center"
          prop="createTime"
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
          width="100px"
        >
          <template slot-scope="{row}">
            <!--   <i @click="edit(row)" class="el-icon-edit table-operation" style="color: #2db7f5;" v-hasPermission="['goodsInfo:update']" />
            <i @click="singleDelete(row)" class="el-icon-delete table-operation" style="color: #f50;" v-hasPermission="['goodsInfo:delete']" />-->
            <i v-hasPermission="['goodsInfo:update']" style="color: #009EFF;" @click="edit(row)">修改</i>
            <el-divider direction="vertical"></el-divider>
            <i
              v-hasPermission="['goodsInfo:delete']"
              style="color: #E05635;"
              @click="singleDelete(row)"
            >删除</i>
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
      <goodsInfo-edit
        ref="edit"
        :dialog-visible="dialog.isVisible"
        :type="dialog.type"
        @close="editClose"
        @success="editSuccess"
      />
    </el-card>
  </div>
</template>

<script>
  import Treeselect from '@riophae/vue-treeselect'
  import '@riophae/vue-treeselect/dist/vue-treeselect.css'
  import Pagination from '@/components/Pagination'
  import goodsInfoEdit from './Edit'
  import goodsInfoApi from '@/api/GoodsInfo.js'

  export default {
    name: 'goodsInfoManage',
    components: {Pagination, goodsInfoEdit, Treeselect},
    data() {
      return {
        dialog: {
          isVisible: false,
          type: 'add'
        },
        tableKey: 0,
        // total: 0,
        orgList: [],
        queryParams: {},
        sort: {},
        selection: [],
        // 以下已修改
        loading: false,
        tableData: {
          total: 0
        },
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
      loadListOptions({callback}) {
        callback()
      },
      filterStatus(value, row) {
        return row.status === value
      },
      editClose() {
        this.dialog.isVisible = false
      },
      editSuccess() {
        this.search()
      },
      onSelectChange(selection) {
        this.selection = selection
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
      exportExcel() {
        this.$message({
          message: '待完善',
          type: 'warning'
        })
      },
      singleDelete(row) {
        if (row.id == '2' || row.id == '3') {
          this.$message({
            message: '禁止删除',
            type: 'warning'
          })
          return
        }
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
            const ids = []
            this.selection.forEach(u => {
              ids.push(u.id)
            })
            this.delete(ids)
          })
          .catch(() => {
            this.clearSelections()
          })
      },
      clearSelections() {
        this.$refs.table.clearSelection()
      },
      delete(ids) {
        goodsInfoApi.delete({ids: ids}).then(response => {
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
      add() {
        this.dialog.type = 'add'
        this.dialog.isVisible = true
        this.$refs.edit.setgoodsInfo(false, this.orgList)
      },
      edit(row) {
        row.auditStatus = row.auditStatus ? 1 : 0
        row.publishStatus = row.publishStatus ? 1 : 0
        this.$refs.edit.setgoodsInfo(row, this.orgList)
        this.dialog.type = 'edit'
        this.dialog.isVisible = true
      },
      fetch(params = {}) {
        this.loading = true
        params.size = this.pagination.size
        params.current = this.pagination.current
        if (this.queryParams.timeRange) {
          params.startCreateTime = this.queryParams.timeRange[0]
          params.endCreateTime = this.queryParams.timeRange[1]
        }
        goodsInfoApi.page(params).then(response => {
          const res = response.data
          this.loading = false
          if (res.isError) {
            return
          }
          this.tableData = res.data
        })
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
  .el-table {
    border: 1px solid #f7f6f9;
  }

  .el-table tr,
  .el-table td {
    border-top: 0;
    border-right: 0;
    border-bottom: 1px solid #f7f6f9;
  }
</style>
