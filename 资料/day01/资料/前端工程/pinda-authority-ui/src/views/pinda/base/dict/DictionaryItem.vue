<template>
  <div class="app-container">
    <div v-if="queryParams.dictionaryId === -1">
      <div class="my-code">点击字典查看详情</div>
    </div>
    <div v-else>
      <div class="filter-container">
        <el-input
          v-model="queryParams.code"
          :placeholder="$t('table.dictionaryItem.code')"
          class="filter-item search-item"
        />
        <el-input
          v-model="queryParams.name"
          :placeholder="$t('table.dictionaryItem.name')"
          class="filter-item search-item"
        />
        <!-- <el-date-picker
        :range-separator="null"
        class="filter-item search-item date-range-item"
        end-placeholder="结束日期"
        format="yyyy-MM-dd HH:mm:ss"
        start-placeholder="开始日期"
        type="daterange"
        v-model="queryParams.timeRange"
        value-format="yyyy-MM-dd HH:mm:ss"
        />-->
        <el-button class="filter-item" plain type="primary" @click="search">{{ $t('table.search') }}</el-button>
        <el-button class="filter-item" plain type="warning" @click="reset">{{ $t('table.reset') }}</el-button>
        <el-dropdown
          v-has-any-permission="['dict:add','dict:delete','dict:export']"
          class="filter-item"
          trigger="click"
        >
          <el-button>
            {{ $t('table.more') }}
            <i class="el-icon-arrow-down el-icon--right" />
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              v-has-permission="['dict:add']"
              @click.native="add"
            >{{ $t('table.add') }}</el-dropdown-item>
            <el-dropdown-item
              v-has-permission="['dict:delete']"
              @click.native="batchDelete"
            >{{ $t('table.delete') }}</el-dropdown-item>
            <el-dropdown-item
              v-has-permission="['dict:export']"
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
        size="mini"
        style="width: 100%;"
        @selection-change="onSelectChange"
        @sort-change="sortChange"
      >
        <el-table-column align="center" type="selection" width="40px" />
        <el-table-column
          :label="$t('table.dictionaryItem.code')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.code }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.dictionaryItem.name')"
          :show-overflow-tooltip="true"
          align="center"
          prop="name"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.name }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.dictionaryItem.describe')"
          :show-overflow-tooltip="true"
          align="center"
          prop="describe"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.describe }}</span>
          </template>
        </el-table-column>
        <el-table-column
          :filter-method="filterStatus"
          :filters="[{ text: $t('common.status.valid'), value: true }, { text: $t('common.status.invalid'), value: false }]"
          :label="$t('table.dictionaryItem.status')"
          class-name="status-col"
          width="70px"
        >
          <template slot-scope="{row}">
            <el-tag
              :type="row.status | statusFilter"
            >{{ row.status ? $t('common.status.valid') : $t('common.status.invalid') }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          :label="$t('table.createTime')"
          align="center"
          prop="createTime"
          sortable="custom"
          width="160px"
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
            <i
              v-hasPermission="['dict:update']"
              class="el-icon-edit table-operation"
              style="color: #2db7f5;"
              @click="edit(row)"
            />
            <i
              v-hasPermission="['dict:delete']"
              class="el-icon-delete table-operation"
              style="color: #f50;"
              @click="singleDelete(row)"
            />
            <el-link
              v-has-no-permission="['dict:update','dict:delete']"
              class="no-perm"
            >{{ $t('tips.noPermission') }}</el-link>
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
      <dictionaryItem-edit
        ref="edit"
        :dialog-visible="dialog.isVisible"
        :type="dialog.type"
        @close="editClose"
        @success="editSuccess"
      />
    </div>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import DictionaryItemEdit from './DictionaryItemEdit'
import dictionaryItemApi from '@/api/DictionaryItem.js'

export default {
  name: 'DictionaryItemManage',
  components: { Pagination, DictionaryItemEdit },
  filters: {
    statusFilter(status) {
      const map = {
        false: 'danger',
        true: 'success'
      }
      return map[status] || 'success'
    }
  },
  data() {
    return {
      dialog: {
        isVisible: false,
        type: 'add'
      },
      tableKey: 0,
      queryParams: {
        dictionaryId: -1
      },
      sort: {},
      selection: [],
      // 以下已修改
      loading: false,
      dictionary: {
        id: null,
        code: ''
      },
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
  watch: {},
  mounted() {
    this.fetch({
      ...this.queryParams,
      ...this.sort
    })
  },
  methods: {
    filterStatus(value, row) {
      return row.status === value
    },
    editClose() {
      this.dialog.isVisible = false
    },
    editSuccess() {
      debugger
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
      this.queryParams = { dictionaryId: this.dictionary.id }
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
      dictionaryItemApi.delete({ ids: ids }).then(response => {
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
      this.$refs.edit.setDictionaryItem({
        id: null,
        status: true,
        dictionaryId: this.dictionary.id,
        dictionaryCode: this.dictionary.code
      })
    },
    edit(row) {
      this.$refs.edit.setDictionaryItem(row)
      this.dialog.type = 'edit'
      this.dialog.isVisible = true
    },
    fetch(params = {}) {
      this.loading = true

      params.size = this.pagination.size
      params.current = this.pagination.current
      // if (this.queryParams.timeRange) {
      //   params.startCreateTime = this.queryParams.timeRange[0]
      //   params.endCreateTime = this.queryParams.timeRange[1]
      // }
      dictionaryItemApi.page(params).then(response => {
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
    },
    dictionaryClick(dictionary) {
      this.queryParams.dictionaryId = dictionary.id
      this.dictionary = dictionary
      this.search()
    }
  }
}
</script>
<style lang="scss" scoped>
</style>
