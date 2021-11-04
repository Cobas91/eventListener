import { useEffect, useState } from 'react'
import {
  API_getAllNotificationUser,
  API_addNotificationUser,
  API_editNotificationUser,
  API_getSingleUserInformation,
} from '../../service/notificationUserService'
import { API_getAllEvents } from '../../service/eventService'

export default function useNotificationUsers() {
  const [notificationUser, setNotificationUser] = useState([])
  const [events, setEvents] = useState([])

  useEffect(() => {
    getAllNotificationUser()
    getAllEvents()
  }, [])

  const getAllNotificationUser = () => {
    API_getAllNotificationUser().then(res => {
      if (res) {
        setNotificationUser(res)
      }
    })
  }

  const getAllEvents = () => {
    API_getAllEvents().then(res => {
      if (res) {
        setEvents(res)
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
    events,
  }
}
