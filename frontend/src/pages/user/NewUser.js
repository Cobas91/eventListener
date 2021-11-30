/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import { useEffect, useState } from 'react'
import { Button, TextField, Typography } from '@mui/material'
import styled from 'styled-components/macro'
import useNotificationUsers from '../../components/hooks/useNotificationUsers'
import { DataGrid } from '@mui/x-data-grid'
import TableToolbar from '../../utils/table/TableToolbar'
import useEvents from '../../components/hooks/useEvents'
import { getEventHeaders, getFilters } from '../../utils/table/tableHelper'
import { useHistory } from 'react-router-dom'

export default function NewUser() {
  const { getAllEvents } = useEvents()
  const { addNotificationUser } = useNotificationUsers()
  const history = useHistory()
  const [newUser, setNewUser] = useState({})
  const [selectedEvents, setSelectedEvents] = useState([])
  const [allAvailableEvents, setAllAvailableEvents] = useState([])
  useEffect(() => {
    getAllEvents().then(events => {
      setAllAvailableEvents(events)
    })
    // eslint-disable-next-line
  }, [])
  const handleSubmit = e => {
    e.preventDefault()
    newUser.listenEvents = selectedEvents
    addNotificationUser(newUser)
    history.push('/administration')
  }
  const handleOnChange = e => {
    setNewUser({ ...newUser, [e.target.id]: e.target.value })
  }
  return (
    <NewUserContainer>
      <StyledInfoBox>
        <Typography variant="h5">Neuen User anlegen</Typography>
        <Typography>
          Hier kann ein neuer User zur Benachrichtigung angelegt werden. Ein
          User kann bei mehreren Events benachrichtigt werden.
        </Typography>
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
          rows={allAvailableEvents}
          columns={getEventHeaders()}
          pageSize={5}
          rowsPerPageOptions={[5]}
          density="compact"
          checkboxSelection
          onSelectionModelChange={event => {
            setSelectedEvents(event)
          }}
          components={{
            Toolbar: () => TableToolbar(getFilters()),
          }}
        />
      </TableContainer>
      <StyledButton type="button" variant="contained" onClick={handleSubmit}>
        Speichern
      </StyledButton>
    </NewUserContainer>
  )
}

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
