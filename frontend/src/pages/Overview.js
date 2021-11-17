import * as React from 'react'
import { DataGrid } from '@mui/x-data-grid'
import styled from 'styled-components'
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Typography,
} from '@mui/material'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'
import useEvents from '../components/hooks/useEvents'
import TableToolbar from '../components/TableToolbar'
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
      <Dialog
        open={dialogUser}
        onClose={() => {
          setDialogUser(false)
        }}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{'User entfernen?'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Der User wird aus der Datenbank gelöscht und alle verlinkungen zu
            Events werden entfernt.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              setDialogUser(false)
            }}
          >
            Nein
          </Button>
          <Button onClick={handleDelUser}>Ja</Button>
        </DialogActions>
      </Dialog>
      <Dialog
        open={dialogEvent}
        onClose={() => {
          setDialogEvent(false)
        }}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">{'Event entfernen?'}</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Das Event wird aus der Datenbank gelöscht, alle verlinkungen zu
            Usern werden entfernt.
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            onClick={() => {
              setDialogEvent(false)
            }}
          >
            Nein
          </Button>
          <Button onClick={handleDelEvent}>Ja</Button>
        </DialogActions>
      </Dialog>
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
