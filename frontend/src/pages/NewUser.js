import * as React from 'react'
import { useState } from 'react'
import { Button, Card, TextField, Typography } from '@mui/material'
import styled from 'styled-components'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { DataGrid } from '@mui/x-data-grid'

export default function NewUser() {
  const { addNotificationUser, events } = useNotificationUsers()
  const [newUser, setNewUser] = useState({})
  const handleSubmit = e => {
    e.preventDefault()
    addNotificationUser(newUser)
  }
  const handleOnChange = e => {
    setNewUser({ ...newUser, [e.target.id]: e.target.value })
  }
  const eventTableColumns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'name', headerName: 'Name', width: 130 },
    { field: 'description', headerName: 'Beschreibung', width: 850 },
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
        <TableContainer>
          <Typography variant="h5">Verfügbare Events</Typography>
          <StyledDataGrid
            rows={events}
            columns={eventTableColumns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection
          />
        </TableContainer>
        <StyledButton type="submit" variant="contained">
          Speichern
        </StyledButton>
      </StyledForm>
    </NewUserContainer>
  )
}

const StyledTypography = styled(Typography)``
const StyledDataGrid = styled(DataGrid)`
  background-color: var(--background-paper);
`
const TableContainer = styled.section`
  height: 300px;
  width: 100%;
`
const StyledInfoBox = styled(Card)`
  margin: 1%;
  padding: 15px;
`

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 60%;
`
const NewUserContainer = styled.section`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`

const StyledButton = styled(Button)`
  margin-top: 5%;
`

const StyledTextField = styled(TextField)`
  margin: 10px;
`
