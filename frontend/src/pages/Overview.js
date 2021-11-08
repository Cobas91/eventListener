import * as React from 'react'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import styled from 'styled-components'
import { Button, Typography } from '@mui/material'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'
import useEvents from '../components/hooks/useEvents'
export default function Overview() {
  const { notificationUser } = useNotificationUsers()
  const { events } = useEvents()
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
      flex: 0.3,
      description:
        'Verfügbare Aktionen die ausgeführt werden können für das spezifische Event',
    },
    { field: 'name', headerName: 'Event Name', flex: 0.3 },
    { field: 'description', headerName: 'Beschreibung', flex: 1 },
  ]
  const [selectedUser, setSelectedUser] = useState()
  const [selectedEvent, setSelectedEvent] = useState()
  const history = useHistory()
  const handleClickUser = () => {
    history.push('/edit-user/?id=' + selectedUser)
  }
  const handleClickEvent = () => {
    history.push('/edit-event/?id=' + selectedEvent)
  }

  return (
    <AdministrationContainer>
      <TableContainer>
        <Typography variant="h5">Notification User</Typography>
        <StyledButton
          variant="contained"
          disabled={!selectedUser}
          onClick={handleClickUser}
        >
          Edit
        </StyledButton>
        <DataGrid
          autoHeight={true}
          rows={notificationUser}
          columns={userTableColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: GridToolbar,
          }}
          onSelectionModelChange={user => {
            setSelectedUser(user)
          }}
          selectionModel={selectedUser}
        />
      </TableContainer>

      <TableContainer>
        <Typography variant="h5">Verfügbare Events</Typography>
        <StyledButton
          variant="contained"
          disabled={!selectedEvent}
          onClick={handleClickEvent}
        >
          Edit
        </StyledButton>
        <DataGrid
          autoHeight={true}
          rows={events}
          columns={eventTableColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: GridToolbar,
          }}
          onSelectionModelChange={event => {
            setSelectedEvent(event)
          }}
          selectionModel={selectedEvent}
        />
      </TableContainer>
    </AdministrationContainer>
  )
}
const AdministrationContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const StyledButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
`

const TableContainer = styled.section`
  margin-top: 20px;
  width: 80%;
`
