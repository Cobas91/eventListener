import axios from 'axios'
import { showError } from '../utils/notificationHandler'
import { createHeader } from '../utils/httpHeader'

const API_getAllEvents = () => {
  return axios
    .get('/api/event', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch Notification User Data.'))
}

const API_getEvents = exludedUserId => {
  return axios
    .get(
      '/api/event/' + exludedUserId,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err =>
      showError(err.message, 'Can´t fetch Notificationuser Event Data.')
    )
}

export { API_getAllEvents, API_getEvents }
