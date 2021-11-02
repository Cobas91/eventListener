import axios from 'axios'
import { createHeader } from '../utils/httpHeader'
import { showError } from '../utils/notificationHandler'

const API_getAllNotificationUser = () => {
  return axios
    .get('/api/user', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Cant fetch Notification User Data.'))
}

const API_addNotificationUser = userToAdd => {
  return axios
    .post('/api/user', userToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Cant add Notification User.'))
}

const API_editNotificationUser = userToEdit => {
  return axios
    .post(
      '/api/user/' + userToEdit.id,
      userToEdit,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err => showError(err.message, 'Cant edit Notification User.'))
}

const API_getSingleUserInformation = userId => {
  return axios
    .get('/api/user/' + userId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err =>
      showError(
        err.message,
        `Cant get single User Information for User ${userId} .`
      )
    )
}

export {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
}
