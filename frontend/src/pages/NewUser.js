import * as React from 'react'
import { useState } from 'react'
import { Button, TextField, Typography } from '@mui/material'
import styled from 'styled-components/macro'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { DataGrid } from '@mui/x-data-grid'
import TableToolbar from '../components/TableToolbar'

export default function NewUser() {
  const { addNotificationUser, events } = useNotificationUsers()
  const [newUser, setNewUser] = useState({})
  const [selectedEvents, setSelectedEvents] = useState()
  const usedFilters = {
    columnFilter: true,
    filter: true,
    csvExport: true,
  }
  const handleSubmit = e => {
    e.preventDefault()
    newUser.listenEvents = selectedEvents
    console.log(newUser)
    addNotificationUser(newUser)
  }
  const handleOnChange = e => {
    setNewUser({ ...newUser, [e.target.id]: e.target.value })
  }
  const eventTableColumns = [
    {
      field: 'id',
      headerName: 'ID',
      flex: 0.1,
      description: 'Entspricht der Indikatoren Nummer',
    },
    {
      field: 'actions',
      headerName: 'Aktionen',
      flex: 0.2,
      description:
        'Verfügbare Aktionen die ausgeführt werden können für das spezifische Event',
    },
    { field: 'name', headerName: 'Event Name', flex: 0.3 },
    { field: 'description', headerName: 'Beschreibung', flex: 1 },
  ]
  return (
    <NewUserContainer>
      <StyledInfoBox>
        <Typography variant="h5">Neuen User anlegen</Typography>
        <StyledTypography>
          Hier kann ein neuer User zur Benachrichtigung angelegt werden. Ein
          User kann bei mehreren Events benachrichtigt werden.
        </StyledTypography>
      </StyledInfoBox>
      <StyledForm onSubmit={handleSubmit}>
        <StyledTextField
          required
          id="name"
          label="Username"
          variant="outlined"
          helperText="Required"
          onChange={handleOnChange}
        />
        <StyledTextField
          required
          id="email"
          label="E-Mail"
          variant="outlined"
          helperText="Required"
          onChange={handleOnChange}
        />
      </StyledForm>
      <TableContainer>
        <Typography variant="h5">Verfügbare Events</Typography>
        <DataGrid
          autoHeight={true}
          rows={events}
          columns={eventTableColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          density="compact"
          checkboxSelection
          onSelectionModelChange={event => {
            setSelectedEvents(event)
          }}
          components={{
            Toolbar: () => TableToolbar(usedFilters),
          }}
        />
      </TableContainer>
      <StyledButton type="button" variant="contained" onClick={handleSubmit}>
        Speichern
      </StyledButton>
    </NewUserContainer>
  )
}

const StyledTypography = styled(Typography)``

const TableContainer = styled.section`
  margin-top: 20px;
  width: 80%;
`
const StyledInfoBox = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  margin: 20px;
`

const StyledForm = styled.form`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 80%;
`
const NewUserContainer = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const StyledButton = styled(Button)`
  margin-top: 20px;
`

const StyledTextField = styled(TextField)`
  margin-right: 20px;
`
