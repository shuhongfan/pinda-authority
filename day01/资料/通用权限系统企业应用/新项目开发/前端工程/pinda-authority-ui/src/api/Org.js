import axiosApi from './AxiosApi.js'

const apiList = {
  allTree: {
    method: 'GET',
    url: `/authority/org/tree`
  },
  getOrg: {
    method: 'GET',
    url: `/authority/org`
  },
  save: {
    method: 'POST',
    url: `/authority/org`
  },
  update: {
    method: 'PUT',
    url: `/authority/org`
  },
  delete: {
    method: 'DELETE',
    url: `/authority/org`
  },
  areaList: {
    method: 'GET',
    url: `/web-manager/common/area/simple`
  },
  managerList: {
    method: 'GET',
    url: `/web-manager/common/user/simple`
  }
}

export default {
  allTree(data) {
    return axiosApi({
      ...apiList.allTree,
      formData: true,
      data
    })
  },
  getOrg(data) {
    return axiosApi({
      ...apiList.getOrg,
      data
    })
  },
  save(data) {
    return axiosApi({
      ...apiList.save,
      data
    })
  },
  update(data) {
    return axiosApi({
      ...apiList.update,
      data
    })
  },
  delete(data) {
    return axiosApi({
      ...apiList.delete,
      data
    })
  },
  areaList(data) {
    return axiosApi({
      ...apiList.areaList,
      data
    })
  },
  managerList(data) {
    return axiosApi({
      ...apiList.managerList,
      data
    })
  }
}
