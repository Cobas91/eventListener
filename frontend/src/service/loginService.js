import axios from 'axios'
import { showError } from '../utils/notificationHandler'
const API_login = credentials => {
  return axios
    .post('/auth/login', credentials)
    .then(res => res.data)
    .catch(err => showError(err.message, 'Login failed.'))
}

export { API_login }
