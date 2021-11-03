import { useEffect, useState } from 'react'
import {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
} from '../../service/notificationUserService'

export default function useNotificationUsers() {
  const [notificationUser, setNotificationUser] = useState([])

  useEffect(() => {
    getAllNotificationUser()
  }, [])

  const getAllNotificationUser = () => {
    API_getAllNotificationUser().then(res => {
      if (res) {
        setNotificationUser(res)
      }
    })
  }

  const addNotificationUser = userToAdd => {
    API_addNotificationUser(userToAdd).then(getAllNotificationUser)
  }

  const editNotificationUser = userToEdit => {
    API_editNotificationUser(userToEdit).then(getAllNotificationUser)
  }

  const getSingleUserInformation = userId => {
    return API_getSingleUserInformation(userId)
  }

  return {
    notificationUser,
    addNotificationUser,
    editNotificationUser,
    getSingleUserInformation,
  }
}
