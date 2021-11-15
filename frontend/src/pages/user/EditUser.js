import * as React from 'react'
import { useLocation } from 'react-router-dom'
import { Button, TextField } from '@mui/material'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import useNotificationUsers from '../../components/hooks/useNotificationUsers'

import TransferList from '../../components/TransferList'
import useEvents from '../../components/hooks/useEvents'

export default function EditUser() {
  const urlQuery = useLocation().search
  const userId = new URLSearchParams(urlQuery).get('id')
  const { editNotificationUser, getSingleUserInformation } =
    useNotificationUsers()
  const { getEventsExcludedUser } = useEvents()

  const [userToEdit, setUserToEdit] = useState({
    name: '',
    email: '',
    listenEvents: [],
  })
  const [allAvailableEvents, setAllAvailableEvents] = useState([])
  const [listenEvents, setListenEvents] = useState([])

  useEffect(() => {
    getSingleUserInformation(userId).then(user => {
      setUserToEdit(user)
      setListenEvents(user.listenEvents)
      getEventsExcludedUser(userId).then(events =>
        setAllAvailableEvents(events)
      )
    })
    // eslint-disable-next-line
  }, [userId])

  const handleOnChange = e => {
    setUserToEdit({ ...userToEdit, [e.target.id]: e.target.value })
  }

  const handleClick = () => {
    userToEdit.listenEvents = listenEvents
    editNotificationUser(userToEdit)
  }
  return (
    <EditUserContainer>
      <StyledForm>
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
        <TransferList
          left={allAvailableEvents}
          right={listenEvents}
          setLeft={setAllAvailableEvents}
          setRight={setListenEvents}
        />

        <StyledButton variant="contained" onClick={handleClick}>
          Save
        </StyledButton>
      </StyledForm>
    </EditUserContainer>
  )
}

const StyledForm = styled.form`
  display: flex;
  flex-direction: column;
  width: 80%;
`
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