import * as React from 'react'
import { DataGrid } from '@mui/x-data-grid'
import styled from 'styled-components'
import { Button, Typography } from '@mui/material'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'
import useEvents from '../components/hooks/useEvents'
import TableToolbar from '../utils/table/TableToolbar'
import {
  getEventHeaders,
  getFilters,
  getUserHeaders,
} from '../utils/table/tableHelper'
import Question from '../components/Question'
export default function Overview() {
  const { notificationUser, deleteUser } = useNotificationUsers()
  const { events, deleteEvent } = useEvents()
  const [selectedUser, setSelectedUser] = useState()
  const [selectedEvent, setSelectedEvent] = useState()
  const [dialogUser, setDialogUser] = React.useState(false)
  const [dialogEvent, setDialogEvent] = React.useState(false)
  const history = useHistory()
  const handleClickUser = () => {
    history.push('/edit-user/?id=' + selectedUser)
  }

  const handleDelUser = () => {
    // eslint-disable-next-line array-callback-return
    const user = notificationUser.filter(
      filterUser => filterUser.id === selectedUser[0]
    )
    deleteUser(user[0]).then(() => {
      setDialogUser(false)
    })
  }
  const handleDelEvent = () => {
    // eslint-disable-next-line array-callback-return
    const event = events.filter(
      filterEvent => filterEvent.id === selectedEvent[0]
    )
    deleteEvent(event[0]).then(() => {
      setDialogEvent(false)
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
            onClick={() => {
              setDialogUser(true)
            }}
          >
            Delete
          </StyledDelButton>
        </ButtonSection>
        <DataGrid
          autoHeight={true}
          rows={notificationUser}
          columns={getUserHeaders()}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: () => TableToolbar(getFilters()),
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
            onClick={() => {
              setDialogEvent(true)
            }}
          >
            Delete
          </StyledDelButton>
        </ButtonSection>
        <DataGrid
          autoHeight={true}
          rows={events}
          columns={getEventHeaders()}
          pageSize={5}
          rowsPerPageOptions={[5]}
          components={{
            Toolbar: () => TableToolbar(getFilters()),
          }}
          onSelectionModelChange={event => {
            setSelectedEvent(event)
          }}
          selectionModel={selectedEvent}
        />
      </TableContainer>
      <Question
        title="User entfernen?"
        message="Der User wird aus der Datenbank gelöscht und alle verlinkungen zu
            Events werden entfernt."
        openState={dialogUser}
        setOpenState={() => {
          setDialogUser(false)
        }}
        handleYes={() => {
          handleDelUser()
        }}
      />
      <Question
        title="Event entfernen?"
        message="Das Event wird gelöscht und alle Verknüpfungen werden entfernt."
        openState={dialogEvent}
        setOpenState={() => {
          setDialogEvent(false)
        }}
        handleYes={() => {
          handleDelEvent()
        }}
      />
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
  background-color: var(--primary-error);
`

const TableContainer = styled.section`
  margin-top: 20px;
  width: 80%;
`
