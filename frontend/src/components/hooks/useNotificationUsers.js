import { useEffect, useState } from 'react'
import {
  API_getAllNotificationUser,
  API_addNotificationUser,
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

  return {
    notificationUser,
    addNotificationUser,
  }
}
