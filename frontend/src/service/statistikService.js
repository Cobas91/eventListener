import axios from 'axios'
import { createHeader } from '../utils/httpHeader'
import { showError } from '../utils/notificationHandler'

const API_getStatistikInformation = () => {
  return axios
    .get('/api/statistik/', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch Statistik Data.'))
}

const API_getStatistikInformationByAction = action => {
  return axios
    .get('/api/statistik/' + action, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err =>
      showError(err.message, 'Can´t fetch Statistik for action ' + action)
    )
}

const API_getBarChartData = () => {
  return axios
    .get('/api/statistik/rechart', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch Statistik for action '))
}

export {
  API_getStatistikInformation,
  API_getStatistikInformationByAction,
  API_getBarChartData,
}
