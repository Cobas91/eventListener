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

const API_getAllNotificationUser = () => {
  return axios
    .get('/api/user', createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t fetch Notification User Data.'))
}

const API_addNotificationUser = userToAdd => {
  return axios
    .post('/api/user', userToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t add Notification User.'))
}

const API_editNotificationUser = userToEdit => {
  return axios
    .put(
      '/api/user/' + userToEdit.id,
      userToEdit,
      createHeader(localStorage.getItem('JWT'))
    )
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t edit Notification User.'))
}

const API_getSingleUserInformation = userId => {
  return axios
    .get('/api/user/' + userId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err =>
      showError(
        err.message,
        `Can´t get single User Information for User ${userId} .`
      )
    )
}

const API_deleteUser = userId => {
  return axios
    .delete('/api/user/' + userId, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err =>
      showError(
        err.message,
        `Can´t delete single User Information for User ${userId} .`
      )
    )
}

export {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
  API_deleteUser,
}
