/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import { Redirect, Route } from 'react-router-dom'
import { isExpired } from 'react-jwt'

export default function PrivateRoute(props) {
  const tokenIsValid = !isExpired(localStorage.getItem('JWT'))
  return tokenIsValid ? <Route {...props} /> : <Redirect to={'/login'} />
}
