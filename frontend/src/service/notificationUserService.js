import axios from 'axios'
import { createHeader } from '../utils/httpHeader'

const API_getAllNotificationUser = () => {
  return axios
    .get('/api/user', createHeader(localStorage.getItem('JWT') || ''))
    .then(res => res.data)
    .catch(err => console.error(err))
}

const API_addNotificationUser = userToAdd => {
  return axios
    .post(
      '/api/user',
      userToAdd,
      createHeader(localStorage.getItem('JWT') || '')
    )
    .catch(err => console.error(err))
}

export { API_getAllNotificationUser, API_addNotificationUser }
