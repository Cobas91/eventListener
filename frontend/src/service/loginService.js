/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import axios from 'axios'
import { showError } from '../utils/notificationHandler'
const API_login = credentials => {
  return axios
    .post('/auth/login', credentials)
    .then(res => res.data)
    .catch(err => showError(err.message, 'Login failed.'))
}

export { API_login }
