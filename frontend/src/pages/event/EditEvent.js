import * as React from 'react'
import { useLocation } from 'react-router-dom'
import { Button, TextField, Typography } from '@mui/material'
import { useEffect, useState } from 'react'
import styled from 'styled-components/macro'

import useEvents from '../../components/hooks/useEvents'
import { DataGrid } from '@mui/x-data-grid'
import useNotificationUsers from '../../components/hooks/useNotificationUsers'
import TableToolbar from '../../components/TableToolbar'

export default function EditEvent() {
  const urlQuery = useLocation().search
  const eventId = new URLSearchParams(urlQuery).get('id')
  const { getEventById, editEvent } = useEvents()
  const { notificationUser } = useNotificationUsers()
  const [eventToEdit, setEventToEdit] = useState({
    name: '',
    description: '',
    notificationUser: [],
    actions: [],
  })
  const [selectedUser, setSelectedUser] = useState([])
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
  const usedFilters = {
    columnFilter: true,
    filter: true,
    csvExport: true,
  }
  useEffect(() => {
    getEventById(eventId).then(event => {
      if (event) setEventToEdit(event)
    })
    // eslint-disable-next-line
  }, [eventId])

  useEffect(() => {
    setSelectedUser(
      eventToEdit.notificationUser.map(user => {
        return user.id
      })
    )
  }, [eventToEdit])
  const handleChange = e => {
    const field = e.target.name
    const value = e.target.value
    setEventToEdit({ ...eventToEdit, [field]: value })
  }
  const handleClick = () => {
    const eventToSave = eventToEdit
    eventToEdit.notificationUser = selectedUser
    console.log('Save: ', eventToSave)
    editEvent(eventToSave)
  }
  return (
    <EventContainer>
      <Typography variant="h5">Event editieren</Typography>
      <StyledForm>
        <StyledSection>
          <StyledTextField
            id="name"
            name="name"
            label="Name"
            variant="outlined"
            value={eventToEdit.name}
            onChange={handleChange}
          />
        </StyledSection>
        <StyledSection>
          <Typography variant="h6">Beschreibung</Typography>
          <StyledTextArea
            id="description"
            name="description"
            value={eventToEdit.description}
            onChange={handleChange}
          />
        </StyledSection>
        <StyledSection>
          <Typography variant="h6">Aktionen</Typography>
          <ul>
            {eventToEdit.actions.map(action => (
              <li key={action}>{action}</li>
            ))}
          </ul>
        </StyledSection>
        <StyledSection>
          <Typography variant="h6">Verlinkte Benutzer</Typography>
          <DataGrid
            checkboxSelection
            disableSelectionOnClick
            autoHeight={true}
            rows={notificationUser}
            columns={userTableColumns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            components={{
              Toolbar: () => TableToolbar(usedFilters),
            }}
            onSelectionModelChange={user => {
              setSelectedUser(user)
            }}
            selectionModel={selectedUser}
          />
        </StyledSection>
      </StyledForm>
      <StyledButton variant="contained" onClick={handleClick}>
        Speichern
      </StyledButton>
    </EventContainer>
  )
}
const StyledButton = styled(Button)`
  margin-bottom: 10px;
  margin-top: 10px;
`

const StyledSection = styled.section`
  margin-top: 10px;
`

const StyledTextArea = styled.textarea`
  resize: none;
  overflow: hidden;
  width: 100%;
`
const StyledTextField = styled(TextField)``
const EventContainer = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 25px;
`

const StyledForm = styled.form`
  width: 80%;
`
