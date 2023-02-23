import axiosApi from './AxiosApi.js'

const apiList = {
  page: {
    url: `/goods/goodsInfo/page`,
    method: 'GET'
  },
  save: {
    url: `/goods/goodsInfo`,
    method: 'POST'
  },
  update: {
    url: `/goods/goodsInfo`,
    method: 'PUT'
  },
  delete: {
    url: `/goods/goodsInfo`,
    method: 'DELETE'
  }

}

export default {
  page (data) {
    return axiosApi({
      ...apiList.page,
      formData: true,
      data
    })
  },
  save (data) {
    return axiosApi({
      ...apiList.save,
      data
    })
  },
  update (data) {
    return axiosApi({
      ...apiList.update,
      data
    })
  },
  delete (data) {
    return axiosApi({
      ...apiList.delete,
      data
    })
  }
}
