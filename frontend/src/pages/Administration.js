import * as React from 'react'
import { DataGrid } from '@mui/x-data-grid'
import styled from 'styled-components'
import { Card, Typography } from '@mui/material'
export default function Administration() {
  const users = [
    {
      id: '1',
      name: 'Heinz',
      email: 'heinz@gmail.com',
    },
    {
      id: '2',
      name: 'Peter',
      email: 'peter@gmail.com',
    },
    {
      id: '3',
      name: 'Heidi',
      email: 'heidi@gmail.com',
    },
  ]

  const userTableColumns = [
    { field: 'id', headerName: 'ID', width: 70 },
    { field: 'name', headerName: 'Name', width: 130 },
    { field: 'email', headerName: 'E-Mail', width: 300 },
  ]
  return (
    <AdministrationContainer>
      <StyledCard>
        <TableContainer>
          <Typography variant="h5">Notification User</Typography>
          <StyledDataGrid
            rows={users}
            columns={userTableColumns}
            pageSize={5}
            rowsPerPageOptions={[5]}
            checkboxSelection
          />
        </TableContainer>
      </StyledCard>
      <StyledCard>
        <TableContainer>
          <Typography variant="h5">Authentication Keys</Typography>
          <StyledDataGrid
            rows={users}
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
