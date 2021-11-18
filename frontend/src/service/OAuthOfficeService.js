import axios from 'axios'
import { showError } from '../utils/notificationHandler'
const API_loginWithOfficeCode = code => {
  return axios
    .get('/oauth/office/' + code)
    .then(res => res.data)
    .catch(err => showError(err.message, 'Login failed.'))
}

export { API_loginWithOfficeCode }
