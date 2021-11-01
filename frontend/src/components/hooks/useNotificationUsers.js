import { useEffect, useState } from 'react'
import { getAllNotificationUser } from '../../service/notificationUserService'

export default function useNotificationUsers() {
  const [notificationUser, setNotificationUser] = useState([])

  useEffect(() => {
    getAllNotificationUser().then(res => {
      if (res) {
        setNotificationUser(res)
      }
    })
  }, [])

  return {
    notificationUser,
  }
}
