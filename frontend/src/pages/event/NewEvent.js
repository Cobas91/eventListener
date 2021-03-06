/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

import * as React from 'react'
import { Button, TextField, Typography } from '@mui/material'
import styled from 'styled-components/macro'
import { useState } from 'react'
import useNotificationUsers from '../../components/hooks/useNotificationUsers'
import TableToolbar from '../../utils/table/TableToolbar'
import { DataGrid } from '@mui/x-data-grid'
import useEvents from '../../components/hooks/useEvents'
import { useHistory } from 'react-router-dom'

export default function NewEvent() {
  const { notificationUser } = useNotificationUsers()
  const { addEvent } = useEvents()
  const history = useHistory()
  const [newEvent, setNewEvent] = useState({
    name: '',
    description: '',
    actions: ['MAIL'],
  })
  const [selectedUser, setSelectedUser] = useState([])

  const handleOnChange = e => {
    setNewEvent({ ...newEvent, [e.target.id]: e.target.value })
  }
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
  const handleClick = () => {
    const eventToAdd = newEvent
    eventToAdd.notificationUser = selectedUser
    addEvent(eventToAdd)
    history.push('/administration')
  }
  return (
    <NewEventContainer>
      <StyledInfoBox>
        <Typography variant="h5">Neues Event anlegen</Typography>
        <Typography>
          Hier kann ein neues Event angelegt werden. Mehrere User können diesem
          Event zugewiesen werden.
        </Typography>
      </StyledInfoBox>
      <StyledForm>
        <Typography>Name</Typography>
        <StyledTextField
          required
          id="name"
          label="Name"
          variant="outlined"
          value={newEvent.name}
          helperText="Required"
          onChange={handleOnChange}
        />
        <Typography>Beschreibung</Typography>
        <StyledTextField
          id="description"
          name="description"
          value={newEvent.description}
          onChange={handleOnChange}
        />
        <Typography>Aktionen</Typography>
        <ul>
          <li>MAIL</li>
        </ul>

        <Typography>Notification User</Typography>
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
      </StyledForm>
      <StyledButton variant="contained" onClick={handleClick}>
        Speichern
      </StyledButton>
    </NewEventContainer>
  )
}

const StyledInfoBox = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
  margin: 20px;
`
const StyledButton = styled(Button)`
  margin-bottom: 10px;
  margin-top: 10px;
`
const NewEventContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`
const StyledTextField = styled(TextField)`
  margin-top: 10px;
  margin-bottom: 10px;
`
const StyledForm = styled.form`
  display: flex;
  justify-content: center;
  flex-direction: column;
  width: 80%;
`
