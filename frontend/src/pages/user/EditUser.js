import * as React from 'react'
import { useHistory, useLocation } from 'react-router-dom'
import { Button, TextField } from '@mui/material'
import styled from 'styled-components'
import { useEffect, useState } from 'react'
import useNotificationUsers from '../../components/hooks/useNotificationUsers'

import TransferList from '../../components/TransferList'
import useEvents from '../../components/hooks/useEvents'
import Question from '../../components/Question'

export default function EditUser() {
  const urlQuery = useLocation().search
  const userId = new URLSearchParams(urlQuery).get('id')
  const { editNotificationUser, getSingleUserInformation, deleteUser } =
    useNotificationUsers()
  const { getEventsExcludedUser } = useEvents()
  const history = useHistory()

  const [userToEdit, setUserToEdit] = useState({
    name: '',
    email: '',
    listenEvents: [],
  })
  const [allAvailableEvents, setAllAvailableEvents] = useState([])
  const [listenEvents, setListenEvents] = useState([])
  const [dialogUser, setDialogUser] = React.useState(false)

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

  const handleDelUser = () => {
    // eslint-disable-next-line array-callback-return
    deleteUser(userToEdit).then(() => {
      setDialogUser(false)
      history.push('/administration')
    })
  }
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
        <StyledDelButton
          variant="contained"
          disabled={userToEdit.listenEvents.length > 0}
          onClick={() => {
            setDialogUser(true)
          }}
        >
          Delete
        </StyledDelButton>
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
    </EditUserContainer>
  )
}

const StyledDelButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
  background-color: var(--primary-error);
`

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
