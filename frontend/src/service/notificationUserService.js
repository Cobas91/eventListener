import axios from 'axios'

const getAllNotificationUser = () => {
  return axios
    .get('/api/user')
    .then(res => res.data)
    .catch(err => console.log(err))
}

export { getAllNotificationUser }
