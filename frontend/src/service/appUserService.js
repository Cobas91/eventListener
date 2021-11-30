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

const API_addAppUser = userToAdd => {
  return axios
    .post('/api/appuser/', userToAdd, createHeader(localStorage.getItem('JWT')))
    .then(response => response.data)
    .catch(err => showError(err.message, 'Can´t create AppUser.'))
}

export { API_addAppUser }
