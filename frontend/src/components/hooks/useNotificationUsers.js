import { useEffect, useState } from 'react'
import {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
} from '../../service/notificationUserService'
import { API_getAllEvents } from '../../service/eventService'
import { showSuccess } from '../../utils/notificationHandler'

export default function useNotificationUsers() {
  const [notificationUser, setNotificationUser] = useState([])

  useEffect(() => {
    getAllNotificationUser()
    getAllEvents()
  }, [])

  const getAllNotificationUser = () => {
    API_getAllNotificationUser().then(res => {
      if (res) {
        setNotificationUser(res)
        return res
      }
    })
  }

  const getAllEvents = () => {
    return API_getAllEvents()
  }

  const addNotificationUser = userToAdd => {
    API_addNotificationUser(userToAdd).then(user => {
      getAllNotificationUser()
      showSuccess('User erstellt: ' + user.name)
    })
  }

  const editNotificationUser = userToEdit => {
    console.log(userToEdit)
    API_editNotificationUser(userToEdit).then(user => {
      getAllNotificationUser()
      //TODO Fehler rendern wenn user res exists
      //showSuccess('User editiert: ' + user.name)
    })
  }

  const getSingleUserInformation = userId => {
    return API_getSingleUserInformation(userId)
  }

  return {
    notificationUser,
    addNotificationUser,
    editNotificationUser,
    getSingleUserInformation,
    getAllEvents,
  }
}
