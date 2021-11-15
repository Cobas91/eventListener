import axios from 'axios'
import { showError } from '../utils/notificationHandler'
import { createHeader } from '../utils/httpHeader'

const API_getAllEvents = () => {
  return axios
    .get('/api/event', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch Notification User Data.'))
}

const API_getEventsExcludeUser = exludedUserId => {
  return axios
    .get(
      '/api/event/not/' + exludedUserId,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err =>
      showError(err.message, 'Can´t fetch Notificationuser Event Data.')
    )
}

const API_getEventById = eventId => {
  return axios
    .get('/api/event/' + eventId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch single Event Data.'))
}

const API_editEvent = eventToEdit => {
  return axios
    .put(
      '/api/event/' + eventToEdit.id,
      eventToEdit,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t edit Event.'))
}

const API_addEvent = eventToAdd => {
  return axios
    .post('/api/event/', eventToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t edit Event.'))
}

const API_deleteEvent = eventId => {
  console.log(eventId)
  return axios
    .delete('/api/event/' + eventId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t delete Event.'))
}
export {
  API_getAllEvents,
  API_getEventsExcludeUser,
  API_getEventById,
  API_editEvent,
  API_addEvent,
  API_deleteEvent,
}
