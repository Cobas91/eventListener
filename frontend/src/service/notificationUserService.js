import axios from 'axios'
import { createHeader } from '../utils/httpHeader'

const API_getAllNotificationUser = () => {
  return axios
    .get('/api/user', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => console.error(err))
}

const API_addNotificationUser = userToAdd => {
  return axios
    .post('/api/user', userToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => console.error(err))
}

const API_editNotificationUser = userToEdit => {
  return axios
    .post(
      '/api/user/' + userToEdit.id,
      userToEdit,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err => console.error(err))
}

const API_getSingleUserInformation = userId => {
  return axios
    .get('/api/user/' + userId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => console.error(err))
}

export {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
}
