import * as React from 'react'
import { DataGrid } from '@mui/x-data-grid'
import styled from 'styled-components'
import { Button, Typography } from '@mui/material'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'
import useEvents from '../components/hooks/useEvents'
import TableToolbar from '../components/TableToolbar'
import { showQuestion } from '../utils/notificationHandler'
export default function Overview() {
  const { notificationUser, deleteUser } = useNotificationUsers()
  const { events, deleteEvent } = useEvents()
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

  const handleDelUser = () => {
    // eslint-disable-next-line array-callback-return
    const user = notificationUser.filter(
      filterUser => filterUser.id === selectedUser[0]
    )
    showQuestion('User ' + user[0]?.name + ' löschen?', () => {
      deleteUser(user[0])
    })
  }
  const handleDelEvent = () => {
    // eslint-disable-next-line array-callback-return
    const event = events.filter(
      filterEvent => filterEvent.id === selectedEvent[0]
    )
    showQuestion('Event ' + event[0].name + ' löschen?', () => {
      deleteEvent(event[0])
    })
  }
  const handleClickEvent = () => {
    history.push('/edit-event/?id=' + selectedEvent)
  }

  return (
    <AdministrationContainer>
      <TableContainer>
        <Typography variant="h5">Notification User</Typography>
        <ButtonSection>
          <StyledButton
            variant="contained"
            disabled={!selectedUser}
            onClick={handleClickUser}
          >
            Edit
          </StyledButton>
          <StyledDelButton
            variant="contained"
            disabled={!selectedUser}
            onClick={handleDelUser}
          >
            Delete
          </StyledDelButton>
        </ButtonSection>
        <DataGrid
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
      </TableContainer>

      <TableContainer>
        <Typography variant="h5">Verfügbare Events</Typography>
        <ButtonSection>
          <StyledButton
            variant="contained"
            disabled={!selectedEvent}
            onClick={handleClickEvent}
          >
            Edit
          </StyledButton>
          <StyledDelButton
            variant="contained"
            disabled={!selectedEvent}
            onClick={handleDelEvent}
          >
            Delete
          </StyledDelButton>
        </ButtonSection>
        <DataGrid
          autoHeight={true}
          rows={events}
          columns={eventTableColumns}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: () => TableToolbar(usedFilters),
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
const ButtonSection = styled.section`
  display: grid;
  grid-template-columns: 1fr 1fr;
`

const AdministrationContainer = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
`
const StyledButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
`

const StyledDelButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
  background-color: #95190c;
`

const TableContainer = styled.section`
  margin-top: 20px;
  width: 80%;
`
