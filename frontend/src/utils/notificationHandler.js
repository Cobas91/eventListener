import { toast } from 'react-toastify'
import { Button } from '@mui/material'
import styled from 'styled-components'
function showError(message, causedBy) {
  const notificationBody = `An Error occurred, ${causedBy} Details: ${message}`
  toast.error(notificationBody, {
    position: 'top-center',
    autoClose: 3000,
    closeOnClick: true,
    pauseOnHover: true,
    progress: undefined,
  })
}

function showWarning(message) {
  toast.warning(message, {
    position: 'top-center',
    autoClose: 2000,
    closeOnClick: true,
    pauseOnHover: true,
    progress: undefined,
  })
}

function showSuccess(message) {
  toast.success(message, {
    position: 'top-center',
    autoClose: 2000,
    closeOnClick: true,
    pauseOnHover: true,
    progress: undefined,
  })
}

function showQuestion(message) {
  const content = ({ handleYes, handleNo }) => (
    <div>
      <QuestionContainer>{message}</QuestionContainer>
      <StyledButton variant="contained" onClick={handleYes}>
        Yes
      </StyledButton>
      <StyledDelButton variant="contained" onClick={handleNo}>
        No
      </StyledDelButton>
    </div>
  )
  toast.warn(content, {
    position: 'top-center',
    closeOnClick: true,
    pauseOnHover: true,
    progress: undefined,
    autoClose: false,
  })
}
const QuestionContainer = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`

const StyledDelButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
  background-color: #95190c;
`

const StyledButton = styled(Button)`
  margin-top: 10px;
  margin-bottom: 10px;
  margin-right: 10px;
`

export { showError, showWarning, showSuccess, showQuestion }
