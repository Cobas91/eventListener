import axios from 'axios'
import { showError } from '../utils/notificationHandler'
import { createHeader } from '../utils/httpHeader'

const API_getAllEvents = () => {
  return axios
    .get('/api/event', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Cant fetch Notification User Data.'))
}

export { API_getAllEvents }
