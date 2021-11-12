import {
  API_getAllEvents,
  API_getEvents,
  API_getEventById,
} from '../../service/eventService'
import { useEffect, useState } from 'react'

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

  const getEventsExcludedUser = userId => API_getEvents(userId)

  const getEventById = eventId => {
    return API_getEventById(eventId)
  }

  return { events, setEvents, getEventsExcludedUser, getEventById }
}
