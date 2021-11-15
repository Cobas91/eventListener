import {
  API_getAllEvents,
  API_getEventsExcludeUser,
  API_getEventById,
  API_editEvent,
} from '../../service/eventService'
import { useEffect, useState } from 'react'
import { showSuccess } from '../../utils/notificationHandler'

export default function useEvents() {
  const [events, setEvents] = useState([])

  useEffect(() => {
    getAllEvents().then(response => {
      setEvents(response)
    })
  }, [])

  const getAllEvents = () => {
    return API_getAllEvents()
  }

  const getEventsExcludedUser = userId => API_getEventsExcludeUser(userId)

  const getEventById = eventId => {
    return API_getEventById(eventId)
  }

  const editEvent = eventToEdit => {
    API_editEvent(eventToEdit).then(event => {
      showSuccess('Event editiert: ' + event.name)
    })
  }

  return { events, setEvents, getEventsExcludedUser, getEventById, editEvent }
}
