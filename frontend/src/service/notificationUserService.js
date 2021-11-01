import axios from 'axios'

const API_getAllNotificationUser = () => {
  return axios
    .get('/api/user')
    .then(res => res.data)
    .catch(err => console.err(err))
}

const API_addNotificationUser = userToAdd => {
  return axios.post('/api/user', userToAdd).catch(err => console.error(err))
}

export { API_getAllNotificationUser, API_addNotificationUser }
