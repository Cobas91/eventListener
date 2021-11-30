/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import { useEffect, useState } from 'react'
import {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
  API_deleteUser,
} from '../../service/notificationUserService'
import { showSuccess } from '../../utils/notificationHandler'

export default function useNotificationUsers() {
  const [notificationUser, setNotificationUser] = useState([])

  useEffect(() => {
    getAllNotificationUser()
  }, [])

  const getAllNotificationUser = () => {
    API_getAllNotificationUser().then(res => {
      if (res) {
        setNotificationUser(res)
        return res
      }
    })
  }

  const addNotificationUser = userToAdd => {
    API_addNotificationUser(userToAdd).then(user => {
      getAllNotificationUser()
      showSuccess('User erstellt: ' + user.name)
    })
  }

  const editNotificationUser = userToEdit => {
    API_editNotificationUser(userToEdit).then(user => {
      getAllNotificationUser()
      showSuccess('User editiert: ' + user.name)
    })
  }

  const getSingleUserInformation = userId => {
    return API_getSingleUserInformation(userId)
  }

  const deleteUser = user => {
    return API_deleteUser(user?.id).then(response => {
      getAllNotificationUser()
      showSuccess('User wurde entfernt.', response)
    })
  }

  return {
    notificationUser,
    addNotificationUser,
    editNotificationUser,
    getSingleUserInformation,
    deleteUser,
  }
}
