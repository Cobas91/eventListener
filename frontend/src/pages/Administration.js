import * as React from 'react'
import { DataGrid, GridToolbar } from '@mui/x-data-grid'
import styled from 'styled-components'
import { Button, Card, Typography } from '@mui/material'
import useNotificationUsers from '../components/hooks/useNotificationUsers'
import { useState } from 'react'
import { useHistory } from 'react-router-dom'
export default function Administration() {
  const { notificationUser } = useNotificationUsers()
  const userTableColumns = [
    { field: 'id', headerName: 'ID', width: 70, hide: true },
    { field: 'name', headerName: 'Name', width: 130 },
    { field: 'email', headerName: 'E-Mail', width: 300 },
  ]
  const [selectedUser, setSelectedUser] = useState()
  const history = useHistory()
  const handleClick = () => {
    history.push('/edit-user/?id=' + selectedUser)
  }
  return (
    <AdministrationContainer>
      <StyledCard>
        <TableContainer>
          <Typography variant="h5">Notification User</Typography>
          <StyledButton
            variant="contained"
            disabled={!selectedUser}
            onClick={handleClick}
          >
            Edit
          </StyledButton>
          <StyledDataGrid
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
      </StyledCard>
      <StyledCard>
        <TableContainer>
          <Typography variant="h5">Authentication Keys</Typography>
          <StyledDataGrid
            rows={notificationUser}
            columns={userTableColumns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection
          />
        </TableContainer>
      </StyledCard>
    </AdministrationContainer>
  )
}
const AdministrationContainer = styled.section``
const StyledButton = styled(Button)`
  margin-bottom: 10px;
  margin-top: 10px;
`
const StyledDataGrid = styled(DataGrid)`
  background-color: var(--background-paper);
`

const StyledCard = styled(Card)`
  padding: 20px;
  margin: 10px;
`
const TableContainer = styled.section`
  height: 360px;
  width: 100%;
`
