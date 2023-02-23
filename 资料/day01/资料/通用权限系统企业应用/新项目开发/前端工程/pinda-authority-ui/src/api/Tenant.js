import axiosApi from './AxiosApi.js'

const apiList = {
  page: `/authority/tenant/page`,
  update: {
    method: 'PUT',
    url: `/authority/tenant`
  },
  save: {
    method: 'POST',
    url: `/authority/tenant`
  },
  saveInit: {
    method: 'POST',
    url: `/authority/tenant/init`
  },
  remove: {
    method: 'DELETE',
    url: `/authority/tenant/remove`
  }
}

export default {
  page (data) {
    return axiosApi({
      method: 'GET',
      url: apiList.page,
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
  saveInit (data) {
    return axiosApi({
      ...apiList.saveInit,
      data
    })
  },
  update (data) {
    return axiosApi({
      ...apiList.update,
      data
    })
  },
  remove (data) {
    return axiosApi({
      ...apiList.remove,
      data
    })
  }
}
