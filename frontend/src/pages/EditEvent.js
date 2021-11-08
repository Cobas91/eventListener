import * as React from 'react'
import { useLocation } from 'react-router-dom'
import { TextField, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import styled from 'styled-components/macro'
import DropdownOption from '../components/DropdownOption'
import useEvents from '../components/hooks/useEvents'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'

export default function EditEvent() {
  const urlQuery = useLocation().search
  const eventId = new URLSearchParams(urlQuery).get('id')
  const { getEventById } = useEvents()
  const [eventToEdit, setEventToEdit] = useState({ notificationUser: [] })
  const userTableColumns = [
    {
      field: 'id',
      headerName: 'ID',
      flex: 0.2,
      hide: true,
    },
    { field: 'name', headerName: 'Name', flex: 0.3 },
    { field: 'email', headerName: 'E-Mail', flex: 1 },
  ]
  useEffect(() => {
    getEventById().then(event => {
      if (event) setEventToEdit(event)
      else
        setEventToEdit({
          id: '30',
          name: 'DoppelZahlung',
          actions: ['MAIL'],
          description:
            'Ein Event das ausgelöst werden kann wenn etwas passiert und das ist super!',
          notificationUser: [{ id: 1 }, { id: 2 }],
        })
    })
    // eslint-disable-next-line
  }, [eventId])
  const handleChange = e => {
    const field = e.target.name
    const value = e.target.value
    setEventToEdit({ ...eventToEdit, [field]: value })
  }
  return (
    <EventContainer>
      <Typography>Event editieren</Typography>
      <form>
        <TextField
          id="name"
          label="Name"
          variant="outlined"
          value={eventToEdit.name}
          onChange={handleChange}
        />
        <TextField
          id="description"
          label="Beschreibung"
          variant="outlined"
          value={eventToEdit.description}
          onChange={handleChange}
        />
        <DropdownOption
          onChange={handleChange}
          selectOptions={eventToEdit.actions}
        />
        <DataGrid
          autoHeight={true}
          rows={eventToEdit.notificationUser}
          columns={userTableColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: GridToolbar,
          }}
          onSelectionModelChange={user => {
            console.log(user)
          }}
          selectionModel={eventToEdit.notificationUser}
        />
      </form>
    </EventContainer>
  )
}

const EventContainer = styled.section``
