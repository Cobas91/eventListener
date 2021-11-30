/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
