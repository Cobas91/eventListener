import axios from 'axios'
import { createHeader } from '../utils/httpHeader'
import { showError } from '../utils/notificationHandler'

const API_addAppUser = userToAdd => {
  return axios
    .post('/api/appuser/', userToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t create AppUser.'))
}

export { API_addAppUser }
