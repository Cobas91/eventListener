import * as React from 'react'
import { useLocation } from 'react-router-dom'
import { Button, TextField } from '@mui/material'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import useNotificationUsers from '../components/hooks/useNotificationUsers'

export default function EditUser() {
  const urlQuery = useLocation().search
  const userId = new URLSearchParams(urlQuery).get('id')
  const { editNotificationUser, getSingleUserInformation } =
    useNotificationUsers()

  const [userToEdit, setUserToEdit] = useState({
    name: '',
    email: '',
  })

  useEffect(() => {
    getSingleUserInformation(userId).then(setUserToEdit)
    // eslint-disable-next-line
  }, [userId])

  const handleOnChange = e => {
    setUserToEdit({ ...userToEdit, [e.target.id]: e.target.value })
  }

  const handleClick = () => {
    editNotificationUser(userToEdit)
  }
  return (
    <EditUserContainer>
      <StyledTextField
        id="name"
        label="Name"
        variant="outlined"
        value={userToEdit.name}
        onChange={handleOnChange}
      />
      <StyledTextField
        id="email"
        label="E-Mail"
        variant="outlined"
        value={userToEdit.email}
        onChange={handleOnChange}
      />
      <StyledButton variant="contained" onClick={handleClick}>
        Save
      </StyledButton>
    </EditUserContainer>
  )
}

const EditUserContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`

const StyledTextField = styled(TextField)`
  margin: 10px;
`

const StyledButton = styled(Button)`
  margin-bottom: 10px;
  margin-top: 10px;
`
