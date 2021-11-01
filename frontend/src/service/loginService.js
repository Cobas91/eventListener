import axios from 'axios'

const API_login = credentials => {
  return axios
    .post('/auth/login', credentials)
    .then(res => res.data)
    .catch(err => console.error(err))
}

export { API_login }
