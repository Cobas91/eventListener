import { toast } from 'react-toastify'
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

export { showError, showWarning, showSuccess }
