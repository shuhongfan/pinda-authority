import axiosApi from './AxiosApi.js'

const apiList = {
  list: {
    url: `/authority/station/list`,
    method: 'GET'
  },
  page: {
    url: `/authority/station/page`,
    method: 'GET'
  },
  save: {
    url: `/authority/station`,
    method: 'POST'
  },
  update: {
    url: `/authority/station`,
    method: 'PUT'
  },
  delete: {
    url: `/authority/station`,
    method: 'DELETE'
  }

}

export default {
  list(data) {
    return axiosApi({
      ...apiList.list,
      formData: true,
      data
    })
  },
  page(data) {
    return axiosApi({
      ...apiList.page,
      formData: true,
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
  }
}
